package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.CirconscriptionDto;
import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.facade.CirconscriptionFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/circonscription")
@SecurityRequirement(name = "Authorization")
public class CirconscriptionController {

    private final CirconscriptionFacade circonscriptionFacade;

    public CirconscriptionController(CirconscriptionFacade circonscriptionFacade) {
        this.circonscriptionFacade = circonscriptionFacade;
    }

    /**
     * Récupère la liste des circonscriptions
     *
     * @param codesRegions les code des regions.
     * @return la liste des circonscriptions.
     */
    @GetMapping("/lister")
    public Set<CirconscriptionDto> recupererCirconscription(@RequestParam(value = "codesRegions", required = false) String codesRegions) {
        return circonscriptionFacade.recupererCirconscriptions(codesRegions);
    }
}
