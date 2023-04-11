package com.escrutin.escrutinbackend.presentation;

import com.escrutin.escrutinbackend.controller.dto.CirconscriptionDto;
import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.controller.dto.CommissionLocaleDto;
import com.escrutin.escrutinbackend.controller.dto.DetailParticipationDto;
import com.escrutin.escrutinbackend.controller.dto.ParticipationDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatCandidatDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatCarteDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatDetailDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatLigneDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatTableauDto;
import com.escrutin.escrutinbackend.controller.dto.TableauBordDto;
import com.escrutin.escrutinbackend.controller.dto.VoixPartiDto;
import com.escrutin.escrutinbackend.controller.dto.ZoneDto;
import com.escrutin.escrutinbackend.entity.Candidat;
import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.JoinResultatCandidatElection;
import com.escrutin.escrutinbackend.entity.LieuVote;
import com.escrutin.escrutinbackend.entity.Resultat;
import com.escrutin.escrutinbackend.entity.TypeZone;
import com.escrutin.escrutinbackend.entity.Zone;
import com.escrutin.escrutinbackend.enums.Decoupage;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class ResultatDtoFactory {

	/**
	 * Retourne les resultats pour la carte selon la couche
	 *
	 * @param resultat les resultat
	 * @param decoupage la couche du découpage
	 * @return la liste des résultats
	 */
	@Transactional(readOnly = true)
	public List<ResultatCarteDto> resultatCarte(Map<Triple<String, String, Long>, Map<Triple<String, String, String>, Pair<Long, Long>>> resultat,
												Decoupage decoupage) {

		return resultat.entrySet().stream()
				.map(ligne -> {
					CodeDesignationDto decoupageDto = new CodeDesignationDto(ligne.getKey().getLeft(), ligne.getKey().getMiddle());
					Long totalVoix = ligne.getValue().values().stream()
							.map(Pair::getLeft)
							.reduce(0L, Long::sum);
					Long suffrage = ligne.getKey().getRight();
					List<ResultatCandidatDto> resultatCandidatDtos = ligne.getValue().entrySet().stream()
							.map(triplePairEntry -> new ResultatCandidatDto(
									triplePairEntry.getKey().getLeft(),
									triplePairEntry.getKey().getMiddle(),
									triplePairEntry.getKey().getRight(),
									triplePairEntry.getValue().getLeft(),
									triplePairEntry.getValue().getRight().intValue(),
									decoupage == Decoupage.REGION ? totalVoix : suffrage
							))
							.collect(toList());
					return new ResultatCarteDto(decoupage == Decoupage.REGION ? decoupageDto : null, decoupage == Decoupage.COMMUNE ? decoupageDto : null, resultatCandidatDtos);
				})
				.collect(toList());
	}

	@Transactional(readOnly = true)
	private List<ResultatCarteDto> resultatPourMunicipale(Set<JoinResultatCandidatElection> joinResultatCandidatElections,
														  Decoupage decoupage) {

		Map<Zone, List<JoinResultatCandidatElection>> resultatsParZone = joinResultatCandidatElections.stream()
				.filter(joinResultatCandidatElection -> joinResultatCandidatElection.commune() != null)
				.collect(groupingBy(joinResultatCandidatElection -> {
					if (decoupage == Decoupage.COMMUNE) {
						return joinResultatCandidatElection.commune();
					}
					if (decoupage == Decoupage.REGION) {
						return joinResultatCandidatElection.region();
					}
					if (decoupage == Decoupage.DEPARTEMENT) {
						return joinResultatCandidatElection.departement();
					}
					else {
						return joinResultatCandidatElection.sousPrefecture();
					}
				}));

		if (decoupage == Decoupage.COMMUNE) {
			// Les resultats sont par score des candidats
			return resultatsParZone.entrySet().stream()
					.map(resultatCandidatElections -> {
						List<ResultatCandidatDto> resultatCandidatDtos = new ArrayList<>();
						long suffragesExprimes = resultatCandidatElections.getValue().stream()
								.collect(groupingBy(JoinResultatCandidatElection::getResultat))
								.keySet()
								.stream()
								.map(Resultat::getSuffragesExprimes)
								.reduce(0L, Long::sum);
						Map<Candidat, List<JoinResultatCandidatElection>> resultatParCandidat = resultatCandidatElections.getValue().stream()
								.collect(groupingBy(JoinResultatCandidatElection::getCandidat));
						resultatParCandidat.forEach((candidat, value) -> {
							long voix = value.stream()
									.map(JoinResultatCandidatElection::getVoix)
									.reduce(0L, Long::sum);
							resultatCandidatDtos.add(new ResultatCandidatDto(candidat.getNom(), candidat.nomPartiPolitique(), voix, suffragesExprimes, candidat.codeCouleur(), candidat.getOrdre()));
						});

						ResultatCandidatDto premier = resultatCandidatDtos.stream()
								.max(Comparator.comparing(ResultatCandidatDto::getScore))
								.orElse(null);
						ResultatCandidatDto deuxieme = resultatCandidatDtos.stream()
								.filter(resultatCandidatDto -> !resultatCandidatDto.equals(premier))
								.max(Comparator.comparing(ResultatCandidatDto::getScore))
								.orElse(null);

						Zone zoneCommune = resultatCandidatElections.getKey();

						return new ResultatCarteDto(premier, deuxieme, resultatCandidatDtos, null, null, null, zoneCommune);
					})
					.collect(toList());
		}
		else {
			// Les resultats sont par representation des parti
			return resultatsParZone.entrySet().stream()
					.map(resultatCandidatElections -> {
						Map<Pair<String, String>, Long> representationParParti = new HashMap<>();
						Map<Zone, List<JoinResultatCandidatElection>> resultatParCommune = resultatCandidatElections.getValue().stream()
								.collect(groupingBy(JoinResultatCandidatElection::commune));

						List<ResultatCarteDto> resultats = resultatParCommune.entrySet().stream()
								.map(resultatCandidatElections2 -> {
									List<ResultatCandidatDto> resultatCandidatDtos2 = new ArrayList<>();
									long suffragesExprimes = resultatCandidatElections2.getValue().stream()
											.collect(groupingBy(JoinResultatCandidatElection::getResultat))
											.keySet()
											.stream()
											.map(Resultat::getSuffragesExprimes)
											.reduce(0L, Long::sum);
									Map<Candidat, List<JoinResultatCandidatElection>> resultatParCandidat = resultatCandidatElections2.getValue().stream()
											.collect(groupingBy(JoinResultatCandidatElection::getCandidat));
									resultatParCandidat.forEach((candidat, value) -> {
										long voix = value.stream()
												.map(JoinResultatCandidatElection::getVoix)
												.reduce(0L, Long::sum);
										resultatCandidatDtos2.add(new ResultatCandidatDto(candidat.getNom(), candidat.nomPartiPolitique(), voix, suffragesExprimes, candidat.codeCouleur(), candidat.getOrdre()));
									});

									ResultatCandidatDto premier = resultatCandidatDtos2.stream()
											.max(Comparator.comparing(ResultatCandidatDto::getScore))
											.orElse(null);
									ResultatCandidatDto deuxieme = resultatCandidatDtos2.stream()
											.filter(resultatCandidatDto -> !resultatCandidatDto.equals(premier))
											.max(Comparator.comparing(ResultatCandidatDto::getScore))
											.orElse(null);

									Zone zone = resultatCandidatElections2.getKey();

									return new ResultatCarteDto(premier, deuxieme, resultatCandidatDtos2,
											zone.codeType().equals(TypeZone.REGION) ? zone : null, zone.codeType().equals(TypeZone.DEPARTEMENT) ? zone : null,
											zone.codeType().equals(TypeZone.SOUS_PREFECTURE) ? zone : null, zone.codeType().equals(TypeZone.COMMUNE) ? zone : null);
								})
								.collect(toList());

						resultats.forEach(resultatCarteDto -> {
							Pair<String, String> partiVainqueur = resultatCarteDto.meilleurParti();
							if (partiVainqueur != null) {
								Long nombreVictoire = representationParParti.containsKey(partiVainqueur) ? representationParParti.get(partiVainqueur) + 1 : 1;
								representationParParti.put(partiVainqueur, nombreVictoire);
							}
						});

						Map.Entry<Pair<String, String>, Long> premier = representationParParti.entrySet().stream()
								.max(Map.Entry.comparingByValue())
								.orElse(null);
						Map.Entry<Pair<String, String>, Long> deuxieme = representationParParti.entrySet().stream()
								.filter(pairLongEntry -> premier != null && !pairLongEntry.getKey().equals(premier.getKey()))
								.max(Map.Entry.comparingByValue())
								.orElse(null);

						ResultatCandidatDto partiPremier = premier != null ?
								new ResultatCandidatDto(premier.getKey().getLeft(), premier.getKey().getLeft(),
										premier.getValue(), representationParParti.values().stream().reduce(0L, Long::sum), premier.getKey().getRight(), 0) : null;

						ResultatCandidatDto partiDeuxieme = deuxieme != null ?
								new ResultatCandidatDto(deuxieme.getKey().getLeft(), deuxieme.getKey().getLeft(),
										deuxieme.getValue(), representationParParti.values().stream().reduce(0L, Long::sum), deuxieme.getKey().getRight(), 0) : null;

						List<ResultatCandidatDto> resultatCandidatDtos = representationParParti.entrySet().stream()
								.map(representation -> new ResultatCandidatDto(representation.getKey().getLeft(), representation.getKey().getLeft(),
										representation.getValue(), representationParParti.values().stream().reduce(0L, Long::sum), representation.getKey().getRight(), 0))
								.collect(toList());

						Zone zone = resultatCandidatElections.getKey();

						return new ResultatCarteDto(partiPremier, partiDeuxieme, resultatCandidatDtos,
								zone.codeType().equals(TypeZone.REGION) ? zone : null, zone.codeType().equals(TypeZone.DEPARTEMENT) ? zone : null,
								zone.codeType().equals(TypeZone.SOUS_PREFECTURE) ? zone : null, zone.codeType().equals(TypeZone.COMMUNE) ? zone : null);
					})
					.collect(toList());
		}
	}

	public ResultatLigneDto resultatGlobal(Circonscription circonscription, CommissionLocale commissionLocale, List<JoinResultatCandidatElection> resultats) {
		Map<Resultat, List<JoinResultatCandidatElection>> resultatListMap = resultats.stream()
				.collect(groupingBy(JoinResultatCandidatElection::getResultat));
		Map<LieuVote, List<JoinResultatCandidatElection>> resultatsParLieuVote = resultats.stream()
				.collect(groupingBy(JoinResultatCandidatElection::lieuVote));
		Long nombreLieuVote = (long) resultatsParLieuVote.keySet().size();
		Long nombreBureauVote = (long) resultatListMap.keySet().size();
		long nombreInscrits = 0L;
		long nombreHommes = 0L;
		long nombreFemmes = 0L;
		long totalVotants = 0L;
		long nombreBulletinsNuls = 0L;
		long nombreBulletinsBlancs = 0L;
		long suffragesExprimes = 0L;

		for (Resultat resultat : resultatListMap.keySet()) {
			nombreInscrits += resultat.getNombreInscrits();
			nombreHommes += resultat.getNombreHommesVotants();
			nombreFemmes += resultat.getNombreFemmesVotantes();
			nombreBulletinsNuls += resultat.getNombreBulletinsNuls();
			nombreBulletinsBlancs += resultat.getNombreBulletinsBlancs();
			totalVotants += resultat.getNombreVotants();
			suffragesExprimes += resultat.getSuffragesExprimes();
		}

		BigDecimal tauxParticipation = nombreInscrits != 0 ? BigDecimal.valueOf(totalVotants).multiply(BigDecimal.valueOf(100)).
				divide(BigDecimal.valueOf(nombreInscrits), new MathContext(4, RoundingMode.HALF_UP)) : null;

		List<ResultatCandidatDto> resultatCandidats = new ArrayList<>();

		long finalSuffragesExprimes = suffragesExprimes;
		resultats.stream()
				.collect(groupingBy(JoinResultatCandidatElection::getCandidat))
				.forEach(((candidat, joinResultatCandidats) -> {
					Long score = joinResultatCandidats.stream()
							.map(JoinResultatCandidatElection::getVoix)
							.reduce(0L, Long::sum);
					resultatCandidats.add(new ResultatCandidatDto(candidat.getNom(), candidat.nomPartiPolitique(), score, finalSuffragesExprimes, candidat.codeCouleur(), candidat.getOrdre()));
				}));

		if (commissionLocale == null) {
			return new ResultatLigneDto(resultats.get(0).region(), circonscription, nombreBureauVote, nombreInscrits, nombreHommes, nombreFemmes, totalVotants,
					tauxParticipation, nombreBulletinsNuls, nombreBulletinsBlancs, suffragesExprimes, resultatCandidats, nombreLieuVote);
		}
		else {
			return new ResultatLigneDto(resultats.get(0).region(), circonscription, commissionLocale, nombreBureauVote, nombreInscrits, nombreHommes, nombreFemmes, totalVotants,
					tauxParticipation, nombreBulletinsNuls, nombreBulletinsBlancs, suffragesExprimes, resultatCandidats, nombreLieuVote);
		}
	}

	public TableauBordDto construireTableauDeBord(Map<String, Long> agregationChiffres,
												  Set<Triple<String, String, Long>> resultatsParPartiOuCandidat,
												  boolean multipleCirconscription) {
		long nombreLieuVote = agregationChiffres.get("nb_lv");
		long nombreBureauVote = agregationChiffres.get("nb_bv");
		long nombreInscrits = agregationChiffres.get("nb_inscrits");
		long nombreHommes = agregationChiffres.get("nb_hommes");
		long nombreFemmes = agregationChiffres.get("nb_femmes");
		long totalVotants = agregationChiffres.get("nb_votants");
		long nombreBulletinsNuls = agregationChiffres.get("nb_bul_nuls");
		long nombreBulletinsBlancs = agregationChiffres.get("nb_bul_blans");
		long suffragesExprimes = agregationChiffres.get("suffrage");

		BigDecimal tauxParticipation = nombreInscrits != 0 ? BigDecimal.valueOf(totalVotants).multiply(BigDecimal.valueOf(100)).
				divide(BigDecimal.valueOf(nombreInscrits), new MathContext(4, RoundingMode.HALF_UP)) : null;

		Long totalCirconscription = resultatsParPartiOuCandidat.stream()
				.map(Triple::getRight)
				.reduce(0L, Long::sum);

		List<VoixPartiDto> voixPartis = resultatsParPartiOuCandidat.stream()
				.map(triplet -> new VoixPartiDto(
						triplet.getRight(),
						triplet.getLeft(),
						triplet.getMiddle(),
						multipleCirconscription ? totalCirconscription : suffragesExprimes
				))
				.collect(toList());

		return new TableauBordDto(nombreLieuVote, nombreBureauVote, nombreInscrits, nombreHommes,
				nombreFemmes, totalVotants, tauxParticipation, nombreBulletinsNuls,
				nombreBulletinsBlancs, suffragesExprimes, voixPartis, multipleCirconscription);
	}

	public ResultatTableauDto resultats(Map<Pair<Triple<String, String, String>, Triple<String,String, Boolean>>, Map<String,Long>> aggregatChiffre,
										Map<Triple<String, String, Boolean>, Map<Triple<String, String, String>, Triple<Long, Long, Long>>> candidats,
										Circonscription circonscription) {
		List<ResultatLigneDto> resultatLigneDtos = new ArrayList<>();

			aggregatChiffre
					.forEach((pairRegroupement, donnes) -> {
						String region = pairRegroupement.getLeft().getLeft();
						String departement = pairRegroupement.getLeft().getMiddle();
						String sousPrefecture = pairRegroupement.getLeft().getRight();

						CodeDesignationDto commissionLocale = pairRegroupement.getRight().getRight() ? null :  new CommissionLocaleDto(pairRegroupement.getRight().getLeft(), pairRegroupement.getRight().getMiddle());
						CodeDesignationDto circonscriptionDto = pairRegroupement.getRight().getRight() ? new CodeDesignationDto(pairRegroupement.getRight().getLeft(), pairRegroupement.getRight().getMiddle()) : new CodeDesignationDto(circonscription.getCode(), circonscription.getDesignation());
						long nombreLieuVote = donnes.get("nb_lv");
						long nombreBureauVote = donnes.get("nb_bv");
						long nombreInscrits = donnes.get("nb_inscrits");
						long nombreHommes = donnes.get("nb_hommes");
						long nombreFemmes = donnes.get("nb_femmes");
						long totalVotants = donnes.get("nb_votants");
						long nombreBulletinsNuls = donnes.get("nb_bul_nuls");
						long nombreBulletinsBlancs = donnes.get("nb_bul_blans");
						long suffragesExprimes = donnes.get("suffrage");

						BigDecimal tauxParticipation = nombreInscrits != 0 ? BigDecimal.valueOf(totalVotants).multiply(BigDecimal.valueOf(100)).
								divide(BigDecimal.valueOf(nombreInscrits), new MathContext(4, RoundingMode.HALF_UP)) : null;

						List<ResultatCandidatDto> resultatCandidatDtos = candidats.get(pairRegroupement.getRight()).entrySet().stream()
								.map(triplePairEntry -> new ResultatCandidatDto(
										triplePairEntry.getKey().getLeft(),
										triplePairEntry.getKey().getMiddle(),
										triplePairEntry.getKey().getRight(),
										triplePairEntry.getValue().getLeft(),
										triplePairEntry.getValue().getRight().intValue(),
										suffragesExprimes
								))
								.collect(toList());

						resultatLigneDtos.add(new ResultatLigneDto(region, departement, sousPrefecture, circonscriptionDto, commissionLocale, nombreBureauVote,
								nombreInscrits, nombreHommes, nombreFemmes, totalVotants, tauxParticipation, nombreBulletinsNuls,
								nombreBulletinsBlancs, suffragesExprimes, resultatCandidatDtos, nombreLieuVote));
					});
			if (circonscription != null ) {
				resultatLigneDtos.sort(Comparator.comparing(ResultatLigneDto::designationCommissionLocale));
			}
			return new ResultatTableauDto(null, false, resultatLigneDtos);
	}

    /**
     * Construit la liste des participations
     *
     * @param resultats
     * @return une liste de participations
     */
    public List<ParticipationDto> participationsDto(Set<Map<String, Object>> resultats) {
        List<ParticipationDto> participationsDtos = new ArrayList<>();
        resultats.forEach(ligne -> {
            ZoneDto region = new ZoneDto((String) ligne.get("code_region"),
                    (String) ligne.get("designation_region"), (Long) ligne.get("id_region"));
            CirconscriptionDto circonscription = new CirconscriptionDto((String) ligne.get("code_circonscription"),
                    (String) ligne.get("designation_circonscription"), (Long) ligne.get("id_circonscription"));
            CommissionLocaleDto commissionLocale = new CommissionLocaleDto((String) ligne.get("code_commission"),
                    (String) ligne.get("designation_commission"), (Long) ligne.get("id_commission"));
            CodeDesignationDto lieuVote = new CodeDesignationDto((String) ligne.get("code_lieu_vote"),
                    (String) ligne.get("designation_lieu_vote"));
            CodeDesignationDto bureauVote = new CodeDesignationDto((String) ligne.get("code_bureau_vote"), (String) ligne.get("designation_bureau_vote"));
            Long nombreInscrits = (Long) ligne.get("nombre_inscrits");
            Long totalVotants = (Long) ligne.get("total_votants");
            BigDecimal taux = (BigDecimal) ligne.get("taux_participation");
            participationsDtos.add(
                    new ParticipationDto(region, circonscription, commissionLocale, bureauVote, lieuVote, nombreInscrits, totalVotants, taux));

        });
        return participationsDtos;
    }

	/**
	 * Construit le détail de la participation
	 *
	 * @param resultat le resultat
	 * @return le détail de la participation
	 */
	public DetailParticipationDto detailParticipationDto(Resultat resultat) {
		return new DetailParticipationDto(resultat);
	}

	/**
	 * Construit le dto de détail du résultat
	 *
	 * @param resultatCel la liste des resultats
	 * @return le détail du résultat
	 */
//	public DetailResultatDto detailResultatsDto(List<ResultatLigneDto> resultatCel) {
//		if (resultatCel != null && !resultatCel.isEmpty()) {
//			long nombreLieuVote = 0L;
//			long nombreBureauVote = 0L;
//			long nombreInscrits = 0L;
////            long nombreHommes = 0L;
////            long nombreFemmes = 0L;
//			long totalVotants = 0L;
//			long nombreBulletinsNuls = 0L;
//			long nombreBulletinsBlancs = 0L;
//			long suffragesExprimes = 0L;
//
//			for (ResultatLigneDto resultatLigneDto : resultatCel) {
//				nombreLieuVote += resultatLigneDto.getNombreLieuVote();
//				nombreBureauVote += resultatLigneDto.getNombreBureauVote();
//				nombreInscrits += resultatLigneDto.getNombreInscrits();
////                nombreHommes += resultatLigneDto.getNombreHommes();
////                nombreFemmes += resultatLigneDto.getNombreFemmes();
//				totalVotants += resultatLigneDto.getTotalVotants();
//				nombreBulletinsBlancs += resultatLigneDto.getNombreBulletinsBlancs();
//				nombreBulletinsNuls += resultatLigneDto.getNombreBulletinsNuls();
//				suffragesExprimes += resultatLigneDto.getSuffragesExprimes();
//			}
//
//			BigDecimal tauxParticipation = nombreInscrits != 0 ? BigDecimal.valueOf(totalVotants).multiply(BigDecimal.valueOf(100)).
//					divide(BigDecimal.valueOf(nombreInscrits), new MathContext(4, RoundingMode.HALF_UP)) : null;
//
//			return new DetailResultatDto(resultatCel.get(0).getCirconscription(), resultatCel.get(0).getRegion(),
//					nombreLieuVote, nombreBureauVote, nombreInscrits, totalVotants, tauxParticipation, nombreBulletinsNuls,
//					nombreBulletinsBlancs, suffragesExprimes, resultatCel);
//		}
//
//		return null;
//	}

    /**
     * Contruit la liste des resultats par bureau de vote
     *
     * @param resultats la liste des resultats
     * @return la liste de resultats
     */
    public List<ResultatDetailDto> resultatsDetails(Set<Map<String, Object>> resultats,
                                                    Map<String, Map<Triple<String, String, String>, Triple<Integer, Long, Long>>> candidats,
                                                    CommissionLocale commissionLocale) {
        CommissionLocaleDto commissionLocaleDto = commissionLocale != null ? new CommissionLocaleDto(commissionLocale) : null;
        List<ResultatDetailDto> resultatDetailDto = new ArrayList<>();
        resultats.forEach(ligne -> {
            Long nombreInscrits = (Long) ligne.get("nombre_inscrits");
            Long totalVotants = (Long) ligne.get("total_votants");
            BigDecimal taux = (BigDecimal) ligne.get("taux_participation");
            Long suffrage = (Long) ligne.get("suffrage");
            long nombreHommes = (Long) ligne.get("nb_hommes");
            long nombreFemmes = (Long) ligne.get("nb_femmes");
            long nombreBulletinsNuls = (Long) ligne.get("nb_bul_nuls");
            long nombreBulletinsBlancs = (Long) ligne.get("nb_bul_blancs");
            String codeLieuVote = (String) ligne.get("code_lieu_vote");
            String designationLieuVote = (String) ligne.get("designation_lieu_vote");
            String codeBureauVote = (String) ligne.get("code_bureau_vote");
            List<ResultatCandidatDto> resultatCandidatDtos = candidats.get((String) ligne.get("code_bureau_vote")).entrySet().stream()
                    .map(triplePairEntry -> new ResultatCandidatDto(
                            triplePairEntry.getKey().getLeft(),
                            triplePairEntry.getKey().getMiddle(),
                            triplePairEntry.getValue().getMiddle(),
                            suffrage,
                            triplePairEntry.getKey().getRight(),
                            triplePairEntry.getValue().getLeft(),
                            triplePairEntry.getValue().getRight()
                    ))
                    .collect(toList());
            resultatDetailDto.add(
                    new ResultatDetailDto(codeLieuVote, designationLieuVote, codeBureauVote, nombreInscrits, nombreHommes,
                            nombreFemmes, totalVotants, taux, nombreBulletinsNuls, nombreBulletinsBlancs, suffrage, resultatCandidatDtos, commissionLocaleDto));
        });

        return resultatDetailDto;
    }
}
