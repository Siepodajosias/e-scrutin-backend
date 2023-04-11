package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.entity.TypeZone;
import com.escrutin.escrutinbackend.entity.Zone;
import com.escrutin.escrutinbackend.repository.ZoneRepository;
import com.escrutin.escrutinbackend.service.SecurityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class ZoneFacade {

    private final ZoneRepository zoneRepository;
    private final SecurityService securityService;

    public ZoneFacade(ZoneRepository zoneRepository, SecurityService securityService) {
        this.zoneRepository = zoneRepository;
        this.securityService = securityService;
    }

    /**
     * Liste toutes les régions.
     *
     * @return la liste des régions.
     */
    @Transactional(readOnly = true)
    public List<CodeDesignationDto> listerRegions() {
        Set<String> codeRegionsAutorisees = securityService.recupererCodeRegionsAutorisesPourUtilisateurConnecte();
        return zoneRepository.listerEnList(TypeZone.REGION)
                .stream()
                .filter(region -> region != null && codeRegionsAutorisees.contains(region.getCode()))
                .map(zone -> new CodeDesignationDto(zone.getCode(), zone.getDesignation()))
                .sorted(Comparator.comparing(CodeDesignationDto::getDesignation))
                .collect(toList());
    }
}
