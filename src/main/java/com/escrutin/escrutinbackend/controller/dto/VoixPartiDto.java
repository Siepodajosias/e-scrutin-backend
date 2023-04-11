package com.escrutin.escrutinbackend.controller.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class VoixPartiDto {

    private Long nombre;
    private String nom;
    private String codeCouleur;

    private BigDecimal pourcentage;

    public VoixPartiDto() {
    }

    public VoixPartiDto(Long nombre, String nom, String codeCouleur, Long total) {
        this.nombre = nombre;
        this.nom = nom;
        this.codeCouleur = codeCouleur;
        this.pourcentage = total != 0 ? valueOf(nombre).multiply(valueOf(100)).divide(valueOf(total), new MathContext(4, RoundingMode.HALF_UP)) : ZERO;

    }

    public Long getNombre() {
        return nombre;
    }

    public String getNom() {
        return nom;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }

    public BigDecimal getPourcentage() {
        return pourcentage;
    }
}
