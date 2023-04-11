package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Resultat;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ResultatDetailDto {

    private String codeLieuVote;
    private String designationLieuVote;
    private String codeBureauVote;
    private Long nombreInscrits;
    private Long nombreHommesVotants;
    private Long nombreFemmesVotantes;
    private Long totalVotants;
    private BigDecimal tauxParticipation;
    private Long nombreBulletinsNuls;
    private Long nombreBulletinsBlancs;
    private Long sufragesExprimes;
    private List<ResultatCandidatDto> resultatCandidats;
    private CommissionLocaleDto commissionLocale;

    public ResultatDetailDto() {
    }

    public ResultatDetailDto(String codeLieuVote, String designationLieuVote, String codeBureauVote, Long nombreInscrits,
                             Long nombreHommesVotants, Long nombreFemmesVotantes, Long totalVotants, BigDecimal tauxParticipation,
                             Long nombreBulletinsNuls, Long nombreBulletinsBlancs, Long sufragesExprimes,
                             List<ResultatCandidatDto> resultatCandidats, CommissionLocaleDto commissionLocale) {
        this.codeLieuVote = codeLieuVote;
        this.designationLieuVote = designationLieuVote;
        this.codeBureauVote = codeBureauVote;
        this.nombreInscrits = nombreInscrits;
        this.nombreHommesVotants = nombreHommesVotants;
        this.nombreFemmesVotantes = nombreFemmesVotantes;
        this.totalVotants = totalVotants;
        this.tauxParticipation = tauxParticipation;
        this.nombreBulletinsNuls = nombreBulletinsNuls;
        this.nombreBulletinsBlancs = nombreBulletinsBlancs;
        this.sufragesExprimes = sufragesExprimes;
        this.resultatCandidats = resultatCandidats;
        this.commissionLocale = commissionLocale;
    }

    public ResultatDetailDto(Resultat resultat) {
        this.codeLieuVote = resultat.codeLieuVote();
        this.designationLieuVote = resultat.designationLieuVote();
        this.codeBureauVote = resultat.codeBureauVoteReduit();
        this.nombreInscrits = resultat.getNombreInscrits();
        this.nombreHommesVotants = resultat.getNombreHommesVotants();
        this.nombreFemmesVotantes = resultat.getNombreFemmesVotantes();
        this.totalVotants = resultat.getNombreVotants();
        this.tauxParticipation = resultat.getTauxParticipation();
        this.nombreBulletinsNuls = resultat.getNombreBulletinsNuls();
        this.nombreBulletinsBlancs = resultat.getNombreBulletinsBlancs();
        this.sufragesExprimes = resultat.getSuffragesExprimes();
        this.resultatCandidats = resultat.getJoinResultatCandidats().stream()
                .map(joinResultatCandidatElection -> new ResultatCandidatDto(joinResultatCandidatElection, sufragesExprimes))
                .sorted(Comparator.comparing(ResultatCandidatDto::getOrdre))
                .collect(toList());
        this.commissionLocale = resultat.commissionLocale() != null ? new CommissionLocaleDto(resultat.commissionLocale()) : null;
    }

    public String getCodeLieuVote() {
        return codeLieuVote;
    }

    public String getDesignationLieuVote() {
        return designationLieuVote;
    }

    public String getCodeBureauVote() {
        return codeBureauVote;
    }

    public Long getNombreInscrits() {
        return nombreInscrits;
    }

    public Long getNombreHommesVotants() {
        return nombreHommesVotants;
    }

    public Long getNombreFemmesVotantes() {
        return nombreFemmesVotantes;
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

    public Long getSufragesExprimes() {
        return sufragesExprimes;
    }

    public List<ResultatCandidatDto> getResultatCandidats() {
        return resultatCandidats;
    }

    public CommissionLocaleDto getCommissionLocale() {
        return commissionLocale;
    }
}
