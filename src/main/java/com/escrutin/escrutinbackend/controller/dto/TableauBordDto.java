package com.escrutin.escrutinbackend.controller.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

public class TableauBordDto {
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
    private List<VoixPartiDto> voixPartis;

    private boolean multipleCircnscription;

    public TableauBordDto() {
    }

    public TableauBordDto(Long nombreLieuVote, Long nombreBureauVote, Long nombreInscrits, Long nombreHommes,
                          Long nombreFemmes, Long totalVotants, BigDecimal tauxParticipation, Long nombreBulletinsNuls,
                          Long nombreBulletinsBlancs, Long suffragesExprimes, List<VoixPartiDto> voixPartis, boolean multipleCircnscription) {
        this.nombreLieuVote = nombreLieuVote;
        this.nombreBureauVote = nombreBureauVote;
        this.nombreInscrits = nombreInscrits;
        this.nombreHommes = nombreHommes;
        this.nombreFemmes = nombreFemmes;
        this.totalVotants = totalVotants;
        this.tauxParticipation = tauxParticipation != null ? tauxParticipation.setScale(2, RoundingMode.HALF_EVEN) : null;
        this.nombreBulletinsNuls = nombreBulletinsNuls;
        this.nombreBulletinsBlancs = nombreBulletinsBlancs;
        this.suffragesExprimes = suffragesExprimes;
        this.voixPartis = voixPartis.stream()
                .sorted(Comparator.comparing(VoixPartiDto::getPourcentage, reverseOrder()))
                .collect(toList());
        this.multipleCircnscription = multipleCircnscription;
    }

    public boolean isMultipleCircnscription() {
        return multipleCircnscription;
    }

    public Long getNombreLieuVote() {
        return nombreLieuVote;
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

    public List<VoixPartiDto> getVoixPartis() {
        return voixPartis;
    }
}
