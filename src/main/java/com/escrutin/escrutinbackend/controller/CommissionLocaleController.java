package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.controller.dto.CommissionLocaleDto;
import com.escrutin.escrutinbackend.facade.CommissionLocaleFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commission-locale")
@SecurityRequirement(name = "Authorization")
public class CommissionLocaleController {

    private final CommissionLocaleFacade commissionLocaleFacade;

    public CommissionLocaleController(CommissionLocaleFacade commissionLocaleFacade) {
        this.commissionLocaleFacade = commissionLocaleFacade;
    }

    /**
     * Récupère la liste des commissions locales
     *
     * @param regionCodes          les codes des regions
     * @param circonscriptionCodes les codes des circonscriptions
     * @return la liste des commissions locales.
     */
    @GetMapping
    public List<CommissionLocaleDto> recupererCommission(@RequestParam(value = "region") String regionCodes,
                                                         @RequestParam(value = "idsCirconscription", required = false) String circonscriptionCodes) {
        return commissionLocaleFacade.recupererCommissionLocales(regionCodes, circonscriptionCodes);
    }
}
