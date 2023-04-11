package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.Zone;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ResultatLigneDto {
    //
    private CodeDesignationDto circonscription;
    private String region;

    private String departement;

    private String sousPrefecture;
    private CodeDesignationDto commissionLocale;

    private Long nombreLieuVote;
    private Long nombreBureauVote;
    private Long nombreInscrits;
    private Long nombreHommes;
    private Long nombreFemmes;
    private Long totalVotants;
    private BigDecimal tauxParticipation;
    private Long nombreBulletinsNuls;
    private Long nombreBulletinsBlancs;
    private Long suffragesExprimes;
    //
    private List<ResultatCandidatDto> resultatCandidats;

    private ResultatCandidatDto meilleurCandidat;

    private boolean vainqueursMultiples;

    public ResultatLigneDto() {
    }

    public ResultatLigneDto(Zone region, Circonscription circonscription, Long nombreBureauVote, Long nombreInscrits, Long nombreHommes, Long nombreFemmes,
                            Long totalVotants, BigDecimal tauxParticipation, Long nombreBulletinsNuls,
                            Long nombreBulletinsBlancs, Long suffragesExprimes, List<ResultatCandidatDto> resultatCandidats, Long nombreLieuVote) {
        this.nombreBureauVote = nombreBureauVote;
        this.nombreInscrits = nombreInscrits;
        this.nombreHommes = nombreHommes;
        this.nombreFemmes = nombreFemmes;
        this.totalVotants = totalVotants;
        this.tauxParticipation = tauxParticipation;
        this.nombreBulletinsNuls = nombreBulletinsNuls;
        this.nombreBulletinsBlancs = nombreBulletinsBlancs;
        this.suffragesExprimes = suffragesExprimes;
        this.resultatCandidats = resultatCandidats;
        this.circonscription = new CodeDesignationDto(circonscription.getCode(), circonscription.getDesignation());
        this.region = region.getDesignation();
        this.nombreLieuVote = nombreLieuVote;
        Long scoreMax = resultatCandidats.stream()
                .map(ResultatCandidatDto::getScore)
                .max(Comparator.comparing(Long::longValue))
                .orElse(0L);
        List<ResultatCandidatDto> vainqueurs = resultatCandidats.stream()
                .filter(resultatCandidatDto -> resultatCandidatDto.getScore().equals(scoreMax))
                .collect(toList());
        this.meilleurCandidat = vainqueurs.size() == 1 ? vainqueurs.get(0) : null;
        this.vainqueursMultiples = vainqueurs.size() > 1;
    }

    public ResultatLigneDto(CodeDesignationDto circonscription, Long nombreBureauVote, Long nombreInscrits, Long nombreHommes, Long nombreFemmes,
                            Long totalVotants, BigDecimal tauxParticipation, Long nombreBulletinsNuls,
                            Long nombreBulletinsBlancs, Long suffragesExprimes, List<ResultatCandidatDto> resultatCandidats, Long nombreLieuVote) {
        this.nombreBureauVote = nombreBureauVote;
        this.nombreInscrits = nombreInscrits;
        this.nombreHommes = nombreHommes;
        this.nombreFemmes = nombreFemmes;
        this.totalVotants = totalVotants;
        this.tauxParticipation = tauxParticipation;
        this.nombreBulletinsNuls = nombreBulletinsNuls;
        this.nombreBulletinsBlancs = nombreBulletinsBlancs;
        this.suffragesExprimes = suffragesExprimes;
        this.resultatCandidats = resultatCandidats;
        this.circonscription = circonscription;
        this.nombreLieuVote = nombreLieuVote;
        Long scoreMax = resultatCandidats.stream()
                .map(ResultatCandidatDto::getScore)
                .max(Comparator.comparing(Long::longValue))
                .orElse(0L);
        List<ResultatCandidatDto> vainqueurs = resultatCandidats.stream()
                .filter(resultatCandidatDto -> resultatCandidatDto.getScore().equals(scoreMax))
                .collect(toList());
        this.meilleurCandidat = vainqueurs.size() == 1 ? vainqueurs.get(0) : null;
        this.vainqueursMultiples = vainqueurs.size() > 1;
    }

    public ResultatLigneDto(Zone region, Circonscription circonscription, CommissionLocale commissionLocale, Long nombreBureauVote, Long nombreInscrits, Long nombreHommes, Long nombreFemmes,
                            Long totalVotants, BigDecimal tauxParticipation, Long nombreBulletinsNuls,
                            Long nombreBulletinsBlancs, Long suffragesExprimes, List<ResultatCandidatDto> resultatCandidats, Long nombreLieuVote) {
        this.nombreBureauVote = nombreBureauVote;
        this.nombreInscrits = nombreInscrits;
        this.nombreHommes = nombreHommes;
        this.nombreFemmes = nombreFemmes;
        this.totalVotants = totalVotants;
        this.tauxParticipation = tauxParticipation != null ? tauxParticipation.setScale(2, RoundingMode.HALF_EVEN) : null;
        this.nombreBulletinsNuls = nombreBulletinsNuls;
        this.nombreBulletinsBlancs = nombreBulletinsBlancs;
        this.suffragesExprimes = suffragesExprimes;
        this.resultatCandidats = resultatCandidats;
        this.circonscription = new CodeDesignationDto(circonscription.getCode(), circonscription.getDesignation());
        this.region = region.getDesignation();
        this.commissionLocale = new CodeDesignationDto(commissionLocale.getCode(), commissionLocale.getDesignation());
        this.nombreLieuVote = nombreLieuVote;
        Long scoreMax = resultatCandidats.stream()
                .map(ResultatCandidatDto::getScore)
                .max(Comparator.comparing(Long::longValue))
                .orElse(0L);
        List<ResultatCandidatDto> vainqueurs = resultatCandidats.stream()
                .filter(resultatCandidatDto -> resultatCandidatDto.getScore().equals(scoreMax))
                .collect(toList());
        this.meilleurCandidat = vainqueurs.size() == 1 ? vainqueurs.get(0) : null;
        this.vainqueursMultiples = vainqueurs.size() > 1;

    }

    public ResultatLigneDto(String region, String departement, String sousPrefecture, CodeDesignationDto circonscription, CodeDesignationDto commissionLocale, Long nombreBureauVote, Long nombreInscrits, Long nombreHommes, Long nombreFemmes,
                            Long totalVotants, BigDecimal tauxParticipation, Long nombreBulletinsNuls,
                            Long nombreBulletinsBlancs, Long suffragesExprimes, List<ResultatCandidatDto> resultatCandidats, Long nombreLieuVote) {
        this.nombreBureauVote = nombreBureauVote;
        this.nombreInscrits = nombreInscrits;
        this.nombreHommes = nombreHommes;
        this.nombreFemmes = nombreFemmes;
        this.totalVotants = totalVotants;
        this.tauxParticipation = tauxParticipation != null ? tauxParticipation.setScale(2, RoundingMode.HALF_EVEN) : null;
        this.nombreBulletinsNuls = nombreBulletinsNuls;
        this.nombreBulletinsBlancs = nombreBulletinsBlancs;
        this.suffragesExprimes = suffragesExprimes;
        this.resultatCandidats = resultatCandidats;
        this.circonscription = circonscription;
        this.commissionLocale = commissionLocale;
        this.nombreLieuVote = nombreLieuVote;
        Long scoreMax = resultatCandidats.stream()
                .map(ResultatCandidatDto::getScore)
                .max(Comparator.comparing(Long::longValue))
                .orElse(0L);
        List<ResultatCandidatDto> vainqueurs = resultatCandidats.stream()
                .filter(resultatCandidatDto -> resultatCandidatDto.getScore().equals(scoreMax))
                .collect(toList());
        this.meilleurCandidat = vainqueurs.size() == 1 ? vainqueurs.get(0) : null;
        this.vainqueursMultiples = vainqueurs.size() > 1;
        this.region = region;
        this.departement = departement;
        this.sousPrefecture = sousPrefecture;
    }

    public ResultatLigneDto(Zone region, Long nombreBureauVote, CommissionLocale commissionLocale, long suffragesExprimes, long totalVotants, List<ResultatCandidatDto> resultatCandidats, long nombreBulletinsBlancs, long nombreFemmes, Long nombreLieuVote, long nombreBulletinsNuls, long nombreHommes, BigDecimal tauxParticipation, Circonscription circonscription, long nombreInscrits) {
        this.nombreBureauVote = nombreBureauVote;
        this.nombreInscrits = nombreInscrits;
        this.nombreHommes = nombreHommes;
        this.nombreFemmes = nombreFemmes;
        this.totalVotants = totalVotants;
        this.tauxParticipation = tauxParticipation != null ? tauxParticipation.setScale(2, RoundingMode.HALF_EVEN) : null;
        this.nombreBulletinsNuls = nombreBulletinsNuls;
        this.nombreBulletinsBlancs = nombreBulletinsBlancs;
        this.suffragesExprimes = suffragesExprimes;
        this.resultatCandidats = resultatCandidats;
        this.circonscription = new CodeDesignationDto(circonscription.getCode(), circonscription.getDesignation());
        this.region = region.getDesignation();
        this.commissionLocale = new CodeDesignationDto(commissionLocale.getCode(), commissionLocale.getDesignation());
        this.nombreLieuVote = nombreLieuVote;
        Long scoreMax = resultatCandidats.stream()
                .map(ResultatCandidatDto::getScore)
                .max(Comparator.comparing(Long::longValue))
                .orElse(0L);
        List<ResultatCandidatDto> vainqueurs = resultatCandidats.stream()
                .filter(resultatCandidatDto -> resultatCandidatDto.getScore().equals(scoreMax))
                .collect(toList());
        this.meilleurCandidat = vainqueurs.size() == 1 ? vainqueurs.get(0) : null;
        this.vainqueursMultiples = vainqueurs.size() > 1;
    }

    /**
     * Retourne le nom du parti politique du vainqueur
     *
     * @return le nom et la couleur du parti du vainqueur
     */
    public Pair<String, String> meilleurParti() {
        return getMeilleurCandidat() != null ? Pair.of(getMeilleurCandidat().getPartiPolitique(), meilleurCandidat.getCodeCouleur()) : null;
    }

    public Long getNombreBureauVote() {
        return nombreBureauVote;
    }

    public Long getNombreInscrits() {
        return nombreInscrits;
    }

    public Long getNombreHommes() {
        return nombreHommes;
    }

    public Long getNombreFemmes() {
        return nombreFemmes;
    }

    public Long getTotalVotants() {
        return totalVotants;
    }

    public BigDecimal getTauxParticipation() {
        return tauxParticipation;
    }

    public Long getNombreBulletinsNuls() {
        return nombreBulletinsNuls;
    }

    public Long getNombreBulletinsBlancs() {
        return nombreBulletinsBlancs;
    }

    public Long getSuffragesExprimes() {
        return suffragesExprimes;
    }

    public List<ResultatCandidatDto> getResultatCandidats() {
        return resultatCandidats;
    }

    public CodeDesignationDto getCirconscription() {
        return circonscription;
    }

    public String getRegion() {
        return region;
    }

    public CodeDesignationDto getCommissionLocale() {
        return commissionLocale;
    }

    public Long getNombreLieuVote() {
        return nombreLieuVote;
    }

    public ResultatCandidatDto getMeilleurCandidat() {
        return meilleurCandidat;
    }

    public boolean isVainqueursMultiples() {
        return vainqueursMultiples;
    }

    public String getDepartement() {
        return departement;
    }

    public String getSousPrefecture() {
        return sousPrefecture;
    }

    public String designationCommissionLocale() {
        return getCommissionLocale() != null ? getCommissionLocale().getDesignation() : null;
    }
}
