package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.ZoneDto;
import com.escrutin.escrutinbackend.facade.CommissionLocaleFacade;
import com.escrutin.escrutinbackend.facade.ElectionFacade;
import com.escrutin.escrutinbackend.facade.RegionFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/region")
@SecurityRequirement(name = "Authorization")
public class RegionController {

    private final RegionFacade regionFacade;

    public RegionController(CommissionLocaleFacade commissionLocaleFacade, ElectionFacade electionFacade, RegionFacade regionFacade) {
        this.regionFacade = regionFacade;
    }

    /**
     * Liste toutes les régions.
     *
     * @return la liste des regions.
     */
    @GetMapping("/lister")
    public List<ZoneDto> listerBureauVotes() {
        return regionFacade.listerRegions();
    }


}
