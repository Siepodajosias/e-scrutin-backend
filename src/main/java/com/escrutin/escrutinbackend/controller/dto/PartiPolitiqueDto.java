package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.PartiPolitique;

public class PartiPolitiqueDto {

    private String nom;
    private String codeCouleur;

    private Long nombreVictoires;
    public PartiPolitiqueDto() {
    }

    public PartiPolitiqueDto(String nom, String codeCouleur, Long nombreVictoires) {
        this.nom = nom;
        this.codeCouleur = codeCouleur;
        this.nombreVictoires = nombreVictoires;
    }

    public String getNom() {
        return nom;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }

    public Long getNombreVictoires() {
        return nombreVictoires;
    }
}
