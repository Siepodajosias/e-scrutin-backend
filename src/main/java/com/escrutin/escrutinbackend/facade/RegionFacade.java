package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.ZoneDto;
import com.escrutin.escrutinbackend.repository.CommissionLocaleRepository;
import com.escrutin.escrutinbackend.repository.ElectionRepository;
import com.escrutin.escrutinbackend.repository.ZoneRepository;
import com.escrutin.escrutinbackend.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.escrutin.escrutinbackend.entity.TypeZone.REGION;
import static java.util.stream.Collectors.toList;

@Service
public class RegionFacade {

    private final ZoneRepository zoneRepository;
    private final SecurityService securityService;


    public RegionFacade(CommissionLocaleRepository commissionLocaleRepository, ElectionRepository electionRepository, ZoneRepository zoneRepository, SecurityService securityService) {
        this.zoneRepository = zoneRepository;
        this.securityService = securityService;
    }

    /**
     * Liste toutes les régions.
     *
     * @return la liste des regions.
     */
    public List<ZoneDto> listerRegions() {
        Set<String> codeRegionsAutorisees = securityService.recupererCodeRegionsAutorisesPourUtilisateurConnecte();
        return zoneRepository.listerEnList(REGION)
                .stream()
                .map(ZoneDto::new)
                .sorted(Comparator.comparing(ZoneDto::getDesignation))
                .collect(toList());
    }
}
