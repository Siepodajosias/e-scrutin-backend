package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.Resultat;

import java.math.BigDecimal;

public class ParticipationDto {

    private ZoneDto region;
    private CirconscriptionDto circonscription;

    private CommissionLocaleDto commissionLocale;
    private CodeDesignationDto bureauVote;
    private CodeDesignationDto lieuVote;
    private Long nombreInscrits;
    private Long totalParticipants;
    private BigDecimal tauxParticipation;

    public ParticipationDto() {
    }

    public ParticipationDto(Resultat resultat) {
        this.lieuVote = new CodeDesignationDto(resultat.lieuVote().getCode(), resultat.lieuVote().getDesignation());

        this.commissionLocale = new CommissionLocaleDto(resultat.commissionLocale().getCode(), resultat.commissionLocale().getDesignation()
        );
        this.circonscription = new CirconscriptionDto(resultat.circonscription());
        this.region = resultat.region() != null ? new ZoneDto(resultat.region()) : null;
        this.bureauVote = new CodeDesignationDto(resultat.getBureauVote().getCode(),
                String.format("%s - %s",  resultat.getBureauVote().getDesignation(), resultat.codeBureauVoteReduit()));
        this.nombreInscrits = resultat.getNombreInscrits();
        this.totalParticipants = resultat.getNombreVotants();
        this.tauxParticipation = resultat.getTauxParticipation();
    }

    public ParticipationDto(ZoneDto region, CirconscriptionDto circonscription, CommissionLocaleDto commissionLocale,
                            CodeDesignationDto bureauVote, CodeDesignationDto lieuVote, Long nombreInscrits,
                            Long totalParticipants, BigDecimal tauxParticipation) {
        this.region = region;
        this.circonscription = circonscription;
        this.commissionLocale = commissionLocale;
        this.bureauVote = bureauVote;
        this.lieuVote = lieuVote;
        this.nombreInscrits = nombreInscrits;
        this.totalParticipants = totalParticipants;
        this.tauxParticipation = tauxParticipation;
    }

    public ZoneDto getRegion() {
        return region;
    }

    public CirconscriptionDto getCirconscription() {
        return circonscription;
    }

    public CommissionLocaleDto getCommissionLocale() {
        return commissionLocale;
    }

    public CodeDesignationDto getBureauVote() {
        return bureauVote;
    }

    public CodeDesignationDto getLieuVote() {
        return lieuVote;
    }

    public Long getNombreInscrits() {
        return nombreInscrits;
    }

    public Long getTotalParticipants() {
        return totalParticipants;
    }

    public BigDecimal getTauxParticipation() {
        return tauxParticipation;
    }
}
