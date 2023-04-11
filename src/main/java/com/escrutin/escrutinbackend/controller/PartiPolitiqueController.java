package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.PartiPolitiqueDto;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.facade.PartiPolitiqueFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parti")
@SecurityRequirement(name = "Authorization")
public class PartiPolitiqueController {

    private PartiPolitiqueFacade partiPolitiqueFacade;

    public PartiPolitiqueController(PartiPolitiqueFacade partiPolitiqueFacade) {
        this.partiPolitiqueFacade = partiPolitiqueFacade;
    }

    /**
     *
     * @param annee
     * @param typeScrutin
     * @return
     */
    @GetMapping("{annee}/{typeScrutin}")
    public List<PartiPolitiqueDto> recupererPartis(@PathVariable(value = "annee") long annee,
                                                   @PathVariable(value = "typeScrutin") TypeScrutin typeScrutin,
                                                   @RequestParam(value = "tour") TourScrutin tourScrutin) {
        return partiPolitiqueFacade.recupererPartis(annee, typeScrutin, tourScrutin);
    }
}
