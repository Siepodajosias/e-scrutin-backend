package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.PartiPolitiqueDto;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.repository.PartiPolitiqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;

@Service
public class PartiPolitiqueFacade {

    private final PartiPolitiqueRepository partiPolitiqueRepository;

    public PartiPolitiqueFacade(PartiPolitiqueRepository partiPolitiqueRepository) {
        this.partiPolitiqueRepository = partiPolitiqueRepository;
    }

    /**
     * @param annee
     * @param typeScrutin
     * @return
     */
    @Transactional(readOnly = true)
    public List<PartiPolitiqueDto> recupererPartis(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin) {
        return partiPolitiqueRepository.recupererPartis(annee, typeScrutin, tourScrutin).stream()
                .map(triple -> new PartiPolitiqueDto(triple.getLeft(), triple.getMiddle(), triple.getRight()))
                .sorted(Comparator.comparing(PartiPolitiqueDto::getNombreVictoires, reverseOrder()))
                .collect(toList());
    }
}
