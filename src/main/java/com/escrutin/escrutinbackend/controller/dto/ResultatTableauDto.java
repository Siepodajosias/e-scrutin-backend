package com.escrutin.escrutinbackend.controller.dto;

import java.util.List;

public class ResultatTableauDto {

    private ResultatCandidatDto meilleurCandidat;

    private boolean vainqueurMultiple;
    private List<ResultatLigneDto> resultats;

    public ResultatTableauDto() {
    }

    public ResultatTableauDto(ResultatCandidatDto meilleurCandidat, boolean vainqueurMultiple, List<ResultatLigneDto> resultats) {
        this.meilleurCandidat = meilleurCandidat;
        this.resultats = resultats;
        this.vainqueurMultiple = vainqueurMultiple;
    }

    public ResultatCandidatDto getMeilleurCandidat() {
        return meilleurCandidat;
    }

    public List<ResultatLigneDto> getResultats() {
        return resultats;
    }

    public boolean isVainqueurMultiple() {
        return vainqueurMultiple;
    }
}
