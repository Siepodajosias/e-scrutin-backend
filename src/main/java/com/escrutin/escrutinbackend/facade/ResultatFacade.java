package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.DetailParticipationDto;
import com.escrutin.escrutinbackend.controller.dto.ParticipationDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatCarteDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatDetailDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatTableauDto;
import com.escrutin.escrutinbackend.controller.dto.TableauBordDto;
import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.Election;
import com.escrutin.escrutinbackend.entity.Resultat;
import com.escrutin.escrutinbackend.enums.Decoupage;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.presentation.ResultatDtoFactory;
import com.escrutin.escrutinbackend.repository.BureauVoteRepository;
import com.escrutin.escrutinbackend.repository.CirconscriptionRepository;
import com.escrutin.escrutinbackend.repository.CommissionLocaleRepository;
import com.escrutin.escrutinbackend.repository.ElectionRepository;
import com.escrutin.escrutinbackend.repository.JoinResultatCandidatElectionRepository;
import com.escrutin.escrutinbackend.repository.ResultatRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class ResultatFacade {

    private final JoinResultatCandidatElectionRepository joinResultatCandidatElectionRepository;
    private final ElectionRepository electionRepository;
    private final ResultatRepository resultatRepository;

    private final CirconscriptionRepository circonscriptionRepository;

    private final CommissionLocaleRepository commissionLocaleRepository;

    private final BureauVoteRepository bureauVoteRepository;

    private final ResultatDtoFactory resultatDtoFactory;

    public ResultatFacade(JoinResultatCandidatElectionRepository joinResultatCandidatElectionRepository,
                          ElectionRepository electionRepository, ResultatRepository resultatRepository,
                          CirconscriptionRepository circonscriptionRepository,
                          CommissionLocaleRepository commissionLocaleRepository, BureauVoteRepository bureauVoteRepository,
                          ResultatDtoFactory resultatDtoFactory) {
        this.joinResultatCandidatElectionRepository = joinResultatCandidatElectionRepository;
        this.electionRepository = electionRepository;
        this.resultatRepository = resultatRepository;
        this.circonscriptionRepository = circonscriptionRepository;
        this.commissionLocaleRepository = commissionLocaleRepository;
        this.bureauVoteRepository = bureauVoteRepository;
        this.resultatDtoFactory = resultatDtoFactory;
    }

    /**
     * Recupère les resultats par decoupage administratif (zone)
     *
     * @param annee       l'année
     * @param typeScrutin le type de scrutin
     * @param tourScrutin le tour du scrutin
     * @param decoupage   indique le decoupage sur lequel on est
     * @return la liste des resultats selon le decoupage
     */
    public List<ResultatCarteDto> recupererResultatsDecoupageAdministratif(long annee, TypeScrutin typeScrutin,
                                                                           TourScrutin tourScrutin, Decoupage decoupage) {

        Optional<Election> electionOptional = electionRepository.findByAnneeAndTypeAndTour(annee, typeScrutin, tourScrutin);

        if (electionOptional.isPresent()) {
            Map<Triple<String, String, Long>, Map<Triple<String, String, String>, Pair<Long, Long>>> resultats = joinResultatCandidatElectionRepository.recupererResultatsPourCarte(annee, typeScrutin, tourScrutin, decoupage);
            return resultatDtoFactory.resultatCarte(resultats, decoupage);
        }

        return null;
    }

    /**
     * @param annee
     * @param typeScrutin
     * @param regionCodes
     * @param circonscriptionCodes
     * @param commissionLocaleCodes
     * @return
     */
    @Transactional(readOnly = true)
    public TableauBordDto construireTableauDeBord(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin, String regionCodes, String circonscriptionCodes, String commissionLocaleCodes) {
        Set<String> idsCirconscription = isNotBlank(circonscriptionCodes) ? Arrays.stream(circonscriptionCodes.split(",")).filter(Objects::nonNull).collect(toSet()) : Collections.emptySet();

        Set<String> idsCommissionLocale = isNotBlank(commissionLocaleCodes) ? Arrays.stream(commissionLocaleCodes.split(",")).filter(Objects::nonNull).collect(toSet()) : Collections.emptySet();

        Set<String> idsRegion = isNotBlank(regionCodes) ? Arrays.stream(regionCodes.split(",")).filter(Objects::nonNull).collect(toSet()) : Collections.emptySet();

        Map<String, Long> aggregationChiffres = joinResultatCandidatElectionRepository.resultatPourTableauBord(annee, tourScrutin, typeScrutin, idsRegion, idsCirconscription, idsCommissionLocale);
        Set<Triple<String, String, Long>> resultats = joinResultatCandidatElectionRepository.recupererResultatParPartiOuCandidat(annee, tourScrutin, typeScrutin, idsRegion, idsCirconscription, idsCommissionLocale);

        return resultatDtoFactory.construireTableauDeBord(aggregationChiffres, resultats, idsCirconscription.size() != 1);
    }

    /**
     * @param annee
     * @param typeScrutin
     * @param regionCodes
     * @param circonscriptionCodes
     * @param commissionLocaleCodes
     * @return
     */
    @Transactional(readOnly = true)
    public ResultatTableauDto recupererResultats(long annee, TypeScrutin typeScrutin, TourScrutin tour, String regionCodes,
                                                 String circonscriptionCodes, String commissionLocaleCodes) {
        Set<String> codesCirconscription = isNotBlank(circonscriptionCodes) ? Arrays.stream(circonscriptionCodes.split(",")).filter(Objects::nonNull).collect(toSet()) : Collections.emptySet();

        Set<String> codesCommissionLocale = isNotBlank(commissionLocaleCodes) ? Arrays.stream(commissionLocaleCodes.split(",")).filter(Objects::nonNull).collect(toSet()) : Collections.emptySet();

        Set<String> codesRegion = isNotBlank(regionCodes) ? Arrays.stream(regionCodes.split(",")).filter(Objects::nonNull).collect(toSet()) : Collections.emptySet();

        Map<Pair<Triple<String, String, String>, Triple<String, String, Boolean>>, Map<String, Long>> aggregatChiffre = joinResultatCandidatElectionRepository.recupererAgregationParRegroupement(annee, tour, typeScrutin, codesRegion, codesCirconscription, codesCommissionLocale);
        Map<Triple<String, String, Boolean>, Map<Triple<String, String, String>, Triple<Long, Long, Long>>> candidats = joinResultatCandidatElectionRepository.recupererResultatParCandidat(annee, tour, typeScrutin, codesRegion, codesCirconscription, codesCommissionLocale);

        Circonscription circonscription = codesCirconscription.size() == 1 ? circonscriptionRepository.rechercherParCode(new ArrayList<>(codesCirconscription).get(0)).orElse(null) : null;

        return resultatDtoFactory.resultats(aggregatChiffre, candidats, circonscription);
    }

    /**
     * Recupere la liste des participations
     *
     * @param annee
     * @param typeScrutin
     * @param regionCodes
     * @param circonscriptionCodes
     * @param commissionLocaleCodes
     * @return
     */
    @Transactional(readOnly = true)
    public List<ParticipationDto> listerParticipations(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin, String regionCodes, String circonscriptionCodes, String commissionLocaleCodes) {
        Set<String> codesCirconscription = StringUtils.isNoneBlank(circonscriptionCodes) ?
                Arrays.stream(circonscriptionCodes.split(","))
                        .filter(Objects::nonNull)
                        .collect(toSet()) :
                Collections.emptySet();

        Set<String> codesCommissionLocale = StringUtils.isNoneBlank(commissionLocaleCodes) ?
                Arrays.stream(commissionLocaleCodes.split(","))
                        .filter(Objects::nonNull)
                        .collect(toSet()) :
                Collections.emptySet();

        Set<String> codesRegion = StringUtils.isNoneBlank(regionCodes) ?
                Arrays.stream(regionCodes.split(","))
                        .filter(Objects::nonNull)
                        .collect(toSet()) :
                Collections.emptySet();
        Set<Map<String, Object>> resultats = joinResultatCandidatElectionRepository.recupererParticipations(annee, tourScrutin, typeScrutin, codesRegion, codesCirconscription, codesCommissionLocale);
        return resultatDtoFactory.participationsDto(resultats);
    }

    /**
     * Récupère le détail de la participation selon le bureau de vote et le scrutin
     *
     * @param bureauId    l'id du bureau de vote
     * @param annee       l'année
     * @param typeScrutin le type de scrutin
     * @return le détail de la participation.
     */
    @Transactional(readOnly = true)
    public DetailParticipationDto recupererDetailParticipation(long bureauId, long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin) {
        BureauVote bureauVote = bureauVoteRepository.getReferenceById(bureauId);
        Election election = electionRepository.findByAnneeAndTypeAndTour(annee, typeScrutin, tourScrutin).get();
        Resultat resultat = resultatRepository.findByBureauVoteAndElection(bureauVote, election).get();
        return resultatDtoFactory.detailParticipationDto(resultat);
    }

    /**
     * Retourne les resultats par bureau de vote de la commission locale
     *
     * @param annee                l'année
     * @param typeScrutin          le type de scrutin
     * @param tourScrutin          le tour du scrutin
     * @param codeCommissionLocale le code de la commission locale
     * @return la liste des resultats des bureau de vote de la commission locale
     */
    @Transactional
    public List<ResultatDetailDto> recupererResultatParBureauVote(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin, String codeCommissionLocale) {
        Set<Map<String, Object>> resultats = joinResultatCandidatElectionRepository.recupererResultatParBureauVote(annee, tourScrutin, typeScrutin, codeCommissionLocale);
        Map<String, Map<Triple<String, String, String>, Triple<Integer, Long, Long>>> candidats = joinResultatCandidatElectionRepository.recupererCandidatParCommission(annee, tourScrutin, typeScrutin, codeCommissionLocale);
        CommissionLocale commissionLocale = commissionLocaleRepository.rechercherParCode(codeCommissionLocale).orElse(null);
        return resultatDtoFactory.resultatsDetails(resultats, candidats, commissionLocale);
    }
}
