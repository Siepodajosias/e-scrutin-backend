package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Zone;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ResultatCarteDto {

    private CodeDesignationDto region;
    private CodeDesignationDto departement;
    private CodeDesignationDto sousPrefecture;
    private CodeDesignationDto commune;
    private ResultatCandidatDto premier;
    private ResultatCandidatDto deuxieme;
    private List<ResultatCandidatDto> resultats;

    public ResultatCarteDto(ResultatCandidatDto premier) {
        this.premier = premier;
    }

    public ResultatCarteDto(ResultatCandidatDto premier, ResultatCandidatDto deuxieme, List<ResultatCandidatDto> resultats,
                            Zone region, Zone departement, Zone sousPrefecture, Zone commune) {
        this.premier = premier;
        this.deuxieme = deuxieme;
        this.resultats = resultats;
        this.region = region != null ? new CodeDesignationDto(region.getCode(), region.getDesignation()) : null;
        this.departement = departement != null ? new CodeDesignationDto(departement.getCode(), departement.getDesignation()) : null;
        this.sousPrefecture = sousPrefecture != null ? new CodeDesignationDto(sousPrefecture.getCode(), sousPrefecture.getDesignation()) : null;
        this.commune = commune != null ? new CodeDesignationDto(commune.getCode(), commune.getDesignation()) : null;

    }

    public ResultatCarteDto(CodeDesignationDto region, CodeDesignationDto commune, List<ResultatCandidatDto> resultats) {
        this.premier = resultats.stream().filter(resultatCandidatDto -> resultatCandidatDto.getClassement() == 1).findFirst().orElse(null);
        this.deuxieme = resultats.stream().filter(resultatCandidatDto -> resultatCandidatDto.getClassement() == 2).findFirst().orElse(null);
        this.resultats = resultats;
        this.region = region;
        this.commune = commune;

    }

    /**
     * Retourne le nom du parti politique du vainqueur
     *
     * @return le nom et la couleur du parti du vainqueur
     */
    public Pair<String, String> meilleurParti() {
        return getPremier() != null ? Pair.of(getPremier().getPartiPolitique(), getPremier().getCodeCouleur()) : null;
    }


    public ResultatCandidatDto getPremier() {
        return premier;
    }

    public ResultatCandidatDto getDeuxieme() {
        return deuxieme;
    }

    public List<ResultatCandidatDto> getResultats() {
        return resultats;
    }

    public CodeDesignationDto getRegion() {
        return region;
    }

    public CodeDesignationDto getDepartement() {
        return departement;
    }

    public CodeDesignationDto getSousPrefecture() {
        return sousPrefecture;
    }

    public CodeDesignationDto getCommune() {
        return commune;
    }
}
