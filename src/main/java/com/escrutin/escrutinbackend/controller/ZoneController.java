package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.facade.ZoneFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/zone")
@SecurityRequirement(name = "Authorization")
public class ZoneController {

    private ZoneFacade zoneFacade;

    public ZoneController(ZoneFacade zoneFacade) {
        this.zoneFacade = zoneFacade;
    }

    /**
     * Liste toutes les régions.
     *
     * @return la liste des regions.
     */
    @GetMapping("/region/lister")
    public List<CodeDesignationDto> listerRegions() {
        return zoneFacade.listerRegions();
    }
}
