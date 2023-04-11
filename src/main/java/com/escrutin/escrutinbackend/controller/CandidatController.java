package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.ResultatCandidatDto;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.facade.CandidatFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/candidat")
@SecurityRequirement(name = "Authorization")
public class CandidatController {

    private final CandidatFacade candidatFacade;

    public CandidatController(CandidatFacade candidatFacade) {
        this.candidatFacade = candidatFacade;
    }

    @GetMapping
    public List<ResultatCandidatDto> recupererLesCandidats(@RequestParam(value = "annee") long annee,
                                                           @RequestParam(value = "type") TypeScrutin type,
                                                           @RequestParam(value = "tour") TourScrutin tourScrutin,
                                                           @RequestParam(value = "circonscription") String codeCirconscription) {
        return candidatFacade.lister(annee, type, tourScrutin, codeCirconscription);
    }
}
