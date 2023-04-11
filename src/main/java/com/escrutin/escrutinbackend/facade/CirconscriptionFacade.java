package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.CirconscriptionDto;
import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.controller.dto.ZoneDto;
import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.repository.BureauVoteRepository;
import com.escrutin.escrutinbackend.repository.ZoneRepository;
import com.escrutin.escrutinbackend.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.escrutin.escrutinbackend.entity.TypeZone.COMMUNE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class CirconscriptionFacade {

    private final BureauVoteRepository bureauVoteRepository;
    private final ZoneRepository zoneRepository;
    private final SecurityService securityService;


    public CirconscriptionFacade(BureauVoteRepository bureauVoteRepository, ZoneRepository zoneRepository, SecurityService securityService) {
        this.bureauVoteRepository = bureauVoteRepository;
        this.zoneRepository = zoneRepository;
        this.securityService = securityService;
    }

    /**
     * Récupère les circonscriptions
     *
     * @param codesRegions la liste des codes des regions
     * @return la liste des circonscriptions
     */
    @Transactional(readOnly = true)
    public Set<CirconscriptionDto> recupererCirconscriptions(String codesRegions) {
        Set<String> codeZonesParents = StringUtils.isNotBlank(codesRegions) ?
                Arrays.stream(codesRegions.split(","))
                        .filter(Objects::nonNull)
                        .collect(toSet()) :
                Collections.emptySet();
        List<String> codeCirconscriptions = securityService.recupererCodeCirconscriptionAutorisesPourUtilisateurConnecte();

        return bureauVoteRepository.lister().stream()
                .filter(bureauVote -> codeZonesParents.contains(bureauVote.codeRegion()))
                .map(BureauVote::circonscription)
                .distinct()
                .filter(circonscription -> circonscription != null && codeCirconscriptions.contains(circonscription.getCode()))
                .map(CirconscriptionDto::new)
                .sorted(Comparator.comparing(CirconscriptionDto::getDesignation))
                .collect(toSet());
    }
}
