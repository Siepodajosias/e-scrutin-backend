package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.DetailParticipationDto;
import com.escrutin.escrutinbackend.controller.dto.ParticipationDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatCarteDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatDetailDto;
import com.escrutin.escrutinbackend.controller.dto.ResultatTableauDto;
import com.escrutin.escrutinbackend.controller.dto.TableauBordDto;
import com.escrutin.escrutinbackend.enums.Decoupage;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.facade.ResultatFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resultat")
@SecurityRequirement(name = "Authorization")
public class ResultatController {

    private final ResultatFacade resultatFacade;

    public ResultatController(ResultatFacade resultatFacade) {
        this.resultatFacade = resultatFacade;
    }

    /**
     * Récupère les resultats des communes
     *
     * @param tourScrutin le tour du scrutin
     * @param typeScrutin le type de scrutin
     * @param annee       l'année
     * @param decoupage   indique le découpage
     * @return les résultats.
     */
    @GetMapping("carte")
    public List<ResultatCarteDto> recupererResultatCommunes(@RequestParam(value = "tour") TourScrutin tourScrutin,
                                                            @RequestParam(value = "type") TypeScrutin typeScrutin,
                                                            @RequestParam(value = "annee") long annee,
                                                            @RequestParam(value = "decoupage") Decoupage decoupage) {
        return resultatFacade.recupererResultatsDecoupageAdministratif(annee, typeScrutin, tourScrutin, decoupage);
    }

    @GetMapping("/tableauBord")
    public TableauBordDto construireTableauDeBord(@RequestParam(value = "region", required = false) String regionCodes,
                                                  @RequestParam(value = "circonscription", required = false) String circonscriptionCodes,
                                                  @RequestParam(value = "commissionLocale", required = false) String commissionLocaleCodes,
                                                  @RequestParam(value = "tour") TourScrutin tour,
                                                  @RequestParam(value = "type") TypeScrutin typeScrutin,
                                                  @RequestParam(value = "annee") long annee) {
        return resultatFacade.construireTableauDeBord(annee, typeScrutin, tour, regionCodes, circonscriptionCodes, commissionLocaleCodes);
    }

    /**
     * Récupère la liste des résultats selon le filtre passé en paramètre
     *
     * @param regionCodes           la liste des ids des régions
     * @param circonscriptionCodes  la liste des ids des circonscriptions
     * @param commissionLocaleCodes la liste des ids des commissions locales
     * @return la liste des résultats.
     */
    @GetMapping("/global/lister")
    public ResultatTableauDto listerResultatsGlobaux(@RequestParam(value = "region", required = false) String regionCodes,
                                                     @RequestParam(value = "circonscription", required = false) String circonscriptionCodes,
                                                     @RequestParam(value = "commissionLocale", required = false) String commissionLocaleCodes,
                                                     @RequestParam(value = "tour") TourScrutin tourScrutin,
                                                     @RequestParam(value = "type") TypeScrutin typeScrutin,
                                                     @RequestParam(value = "annee") long annee) {
        return resultatFacade.recupererResultats(annee, typeScrutin, tourScrutin, regionCodes, circonscriptionCodes, commissionLocaleCodes);
    }

    /**
     * Récupère la liste des participations selon le lieu de vote et le scrutin
     * @param regionCodes
     * @param circonscriptionCodes
     * @param commissionLocaleCodes
     * @param typeScrutin
     * @param annee
     * @return
     */
    @GetMapping("participation/lister")
    public List<ParticipationDto> listerParticipations(@RequestParam(value = "region", required = false) String regionCodes,
                                                       @RequestParam(value = "circonscription", required = false) String circonscriptionCodes,
                                                       @RequestParam(value = "commissionLocale", required = false) String commissionLocaleCodes,
                                                       @RequestParam(value = "tour") TourScrutin tourScrutin,
                                                       @RequestParam(value = "type") TypeScrutin typeScrutin,
                                                       @RequestParam(value = "annee") long annee) {
        return resultatFacade.listerParticipations(annee, typeScrutin, tourScrutin, regionCodes, circonscriptionCodes, commissionLocaleCodes);
    }

    /**
     * Récupère le détail de la participation selon le bureau de vote et le scrutin
     *
     * @param bureauId
     * @param annee
     * @param type
     * @return
     */
    @GetMapping("/participation/detail")
    public DetailParticipationDto recupererDetailParticipation(@RequestParam(value = "bureauVote") long bureauId,
                                                               @RequestParam(value = "tour") TourScrutin tourScrutin,
                                                               @RequestParam(value = "annee") long annee,
                                                               @RequestParam(value = "type") TypeScrutin type)  {
        return resultatFacade.recupererDetailParticipation(bureauId, annee, type, tourScrutin);
    }

    /**
     * Récupère les résultats des bureaux de vote de la commission locale
     *
     * @param codeCommissionLocale le code de la commission locale
     * @param typeScrutin       le type de scrutin
     * @param annee             l'année
     * @return le détail du résultat.
     */
    @GetMapping("resultat-par-bureau")
    public List<ResultatDetailDto> recupererResultatParBureauVote(@RequestParam(value = "commissionLocale") String codeCommissionLocale,
                                                                  @RequestParam(value = "tour") TourScrutin tourScrutin,
                                                                  @RequestParam(value = "type") TypeScrutin typeScrutin,
                                                                  @RequestParam(value = "annee") long annee) {
        return resultatFacade.recupererResultatParBureauVote(annee, typeScrutin, tourScrutin, codeCommissionLocale);
    }
}
