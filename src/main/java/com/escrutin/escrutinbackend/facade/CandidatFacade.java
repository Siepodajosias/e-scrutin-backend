package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.ResultatCandidatDto;
import com.escrutin.escrutinbackend.entity.Resultat;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.repository.ResultatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

@Service
public class CandidatFacade {

    private ResultatRepository resultatRepository;

    public CandidatFacade(ResultatRepository resultatRepository) {
        this.resultatRepository = resultatRepository;
    }

    @Transactional(readOnly = true)
    public List<ResultatCandidatDto> lister(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin, String codeCirconscription) {
        Set<Resultat> resultats = resultatRepository.findAllResultats(annee, tourScrutin, typeScrutin, null,
                singleton(codeCirconscription), null);
        List<ResultatCandidatDto> resultatCandidatDtos = new ArrayList<>();
        resultats.stream()
                .findFirst()
                .ifPresent(resultat -> resultatCandidatDtos.addAll(resultat.getJoinResultatCandidats().stream()
                        .map(joinResultatCandidatElection -> new ResultatCandidatDto(joinResultatCandidatElection, 0))
                        .collect(toList())));

        return resultatCandidatDtos;
    }
}
