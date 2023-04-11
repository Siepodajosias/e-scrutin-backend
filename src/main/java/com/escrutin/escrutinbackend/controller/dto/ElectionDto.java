package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Election;
import com.escrutin.escrutinbackend.entity.PartiPolitique;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.List;

import static javax.persistence.EnumType.STRING;

public class ElectionDto {

    private Long id;
    private String code;
    private TourScrutin tour;
    private TypeScrutin type;
    private long annee;

    public ElectionDto() {
    }

    /**
     * Constructeur paramétré.
     *
     * @param election l'éléection
     */
    public ElectionDto(Election election) {
        this.id = election.getId();
        this.code = election.getCode();
        this.tour = election.getTour();
        this.type = election.getType();
        this.annee = election.getAnnee();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public TourScrutin getTour() {
        return tour;
    }

    public TypeScrutin getType() {
        return type;
    }

    public long getAnnee() {
        return annee;
    }
}
