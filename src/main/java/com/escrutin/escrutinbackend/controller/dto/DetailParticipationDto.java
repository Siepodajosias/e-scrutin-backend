package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.entity.Resultat;

import java.math.BigDecimal;

public class DetailParticipationDto {
    private CodeDesignationDto region;
    private CodeDesignationDto commissionLocale;
    private CodeDesignationDto lieuVote;
    private Long totalInscrits;
    private BigDecimal tauxGlobal;
    private PointageDto pointageMiJournee;
    private PointageDto pointageCloture;

    public DetailParticipationDto() {
    }

    public DetailParticipationDto(Resultat resultat) {
        this.region = resultat.region() != null ? new CodeDesignationDto(resultat.codeRegion(), resultat.region().getDesignation()) : null;
        this.lieuVote = new CodeDesignationDto(resultat.lieuVote().getCode(), resultat.lieuVote().getDesignation());
        this.totalInscrits = resultat.getNombreInscrits();
        this.pointageCloture = new PointageDto(resultat);
        this.tauxGlobal = pointageCloture.getTaux();
        this.commissionLocale = new CommissionLocaleDto(resultat.lieuVote().getCommissionLocale());
    }

    public CodeDesignationDto getRegion() {
        return region;
    }

    public CodeDesignationDto getCommissionLocale() {
        return commissionLocale;
    }

    public CodeDesignationDto getLieuVote() {
        return lieuVote;
    }

    public Long getTotalInscrits() {
        return totalInscrits;
    }

    public BigDecimal getTauxGlobal() {
        return tauxGlobal;
    }

    public PointageDto getPointageMiJournee() {
        return pointageMiJournee;
    }

    public PointageDto getPointageCloture() {
        return pointageCloture;
    }
}
