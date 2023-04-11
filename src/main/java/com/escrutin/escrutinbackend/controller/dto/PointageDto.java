package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Resultat;

import java.math.BigDecimal;

public class PointageDto {

    private long nombreHommes;
    private long nombreFemmes;
    private long totalParticipants;
    private BigDecimal taux;
    private boolean alerte;

    public PointageDto() {
    }

    public PointageDto(Resultat participation) {
        if (participation != null) {
            this.nombreHommes = participation.getNombreHommesVotants();
            this.nombreFemmes = participation.getNombreFemmesVotantes();
            this.totalParticipants = participation.getNombreVotants();
            this.taux = participation.getTauxParticipation();
        }
    }

    public long getNombreHommes() {
        return nombreHommes;
    }

    public long getNombreFemmes() {
        return nombreFemmes;
    }

    public long getTotalParticipants() {
        return totalParticipants;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public boolean isAlerte() {
        return alerte;
    }
}
