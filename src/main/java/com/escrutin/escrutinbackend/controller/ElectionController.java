package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.controller.dto.ElectionDto;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.facade.CommissionLocaleFacade;
import com.escrutin.escrutinbackend.facade.ElectionFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/election")
@SecurityRequirement(name = "Authorization")
public class ElectionController {

    private final ElectionFacade electionFacade;

    public ElectionController(CommissionLocaleFacade commissionLocaleFacade, ElectionFacade electionFacade) {
        this.electionFacade = electionFacade;
    }

    /**
     * Liste toutes les annéées pour lesquells il y a des scrutins.
     *
     * @return la liste des années.
     */
    @GetMapping("/annees")
    public List<Long> listerAnnees() {
        return electionFacade.listerAnnees();
    }

    /**
     * Liste tous les scrutins.
     *
     * @return la liste des scrutins.
     */
    @GetMapping("/lister")
    public List<ElectionDto> listerScrutins() {
        return electionFacade.listerScrutins();
    }

    /**
     * Liste les tours des scrutins selon l'année et le type.
     *
     * @param annee       l'année du scrutin.
     * @param typeScrutin le type de scrutin.
     * @return la liste des tours.
     */
    @GetMapping("/tour/{annee}/{type}")
    public List<CodeDesignationDto> listerTourScrutins(@PathVariable(value = "annee") long annee, @PathVariable(value = "type") TypeScrutin typeScrutin) {
        return electionFacade.listerTourScrutins(annee, typeScrutin);
    }
}
