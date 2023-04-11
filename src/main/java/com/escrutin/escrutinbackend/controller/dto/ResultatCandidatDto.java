package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.JoinResultatCandidatElection;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class ResultatCandidatDto {

    private String candidat;
    private String partiPolitique;

    private String codeCouleur;
    private Long score;

    private Long total;

    private BigDecimal pourcentage;

    private int ordre;

    private Long id;

    private int classement;

    public ResultatCandidatDto() {
    }

    public ResultatCandidatDto(JoinResultatCandidatElection resultatCandidat, long totalVoix) {
        this.candidat = resultatCandidat.nomCandidat();
        this.partiPolitique = resultatCandidat.nomParti();
        this.codeCouleur = resultatCandidat.codeCouleurParti();
        this.score = resultatCandidat.getVoix();
        this.total = totalVoix;
        this.id = resultatCandidat.getCandidat().getId();
        this.ordre = resultatCandidat.ordreCandidat();
        this.pourcentage = totalVoix != 0 ? valueOf(resultatCandidat.getVoix()).multiply(valueOf(100)).divide(valueOf(totalVoix), new MathContext(4, RoundingMode.HALF_UP)) : ZERO;
    }


    public ResultatCandidatDto(String candidat, String partiPolitique, Long score, Long totalVoix, String codeCouleur, int ordre, long id) {
        this.candidat = candidat;
        this.partiPolitique = partiPolitique;
        this.codeCouleur = codeCouleur;
        this.score = score;
        this.total = totalVoix;
        this.ordre = ordre;
        this.pourcentage = totalVoix != 0 ? valueOf(score).multiply(valueOf(100)).divide(valueOf(totalVoix), new MathContext(4, RoundingMode.HALF_UP)) : ZERO;

        this.id = id;
    }

    public ResultatCandidatDto(String candidat, String partiPolitique, Long score, Long totalVoix, String codeCouleur, int ordre) {
        this.candidat = candidat;
        this.partiPolitique = partiPolitique;
        this.codeCouleur = codeCouleur;
        this.score = score;
        this.total = totalVoix;
        this.ordre = ordre;
        this.pourcentage = totalVoix != 0 ? valueOf(score).multiply(valueOf(100)).divide(valueOf(totalVoix), new MathContext(4, RoundingMode.HALF_UP)) : ZERO;
    }

    public ResultatCandidatDto(String candidat, String partiPolitique, String codeCouleur, Long score,  int classement, Long totalVoix) {
        this.candidat = candidat;
        this.partiPolitique = partiPolitique;
        this.codeCouleur = codeCouleur;
        this.score = score;
        this.total = totalVoix;
        this.classement = classement;
        this.pourcentage = totalVoix != 0 ? valueOf(score).multiply(valueOf(100)).divide(valueOf(totalVoix), new MathContext(4, RoundingMode.HALF_UP)) : ZERO;
    }

    public String getCandidat() {
        return candidat;
    }

    public String getPartiPolitique() {
        return partiPolitique;
    }

    public Long getScore() {
        return score;
    }

    public BigDecimal getPourcentage() {
        return pourcentage;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }

    public Long getTotal() {
        return total;
    }

    public Long getId() {
        return id;
    }

    public int getOrdre() {
        return ordre;
    }

    public int getClassement() {
        return classement;
    }
}
