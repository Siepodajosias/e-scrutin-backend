package com.escrutin.escrutinbackend.controller.dto;

public class AnneeDto {
    private String libelle;

    public AnneeDto() {
    }

    public AnneeDto(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
