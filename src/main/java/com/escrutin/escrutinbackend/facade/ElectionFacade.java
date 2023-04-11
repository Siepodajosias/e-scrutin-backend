package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.controller.dto.ElectionDto;
import com.escrutin.escrutinbackend.entity.Election;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import com.escrutin.escrutinbackend.repository.CommissionLocaleRepository;
import com.escrutin.escrutinbackend.repository.ElectionRepository;
import com.escrutin.escrutinbackend.service.SecurityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.escrutin.escrutinbackend.enums.TourScrutin.PREMIER_TOUR;
import static com.escrutin.escrutinbackend.enums.TourScrutin.SECOND_TOUR;
import static java.util.stream.Collectors.toList;

@Service
public class ElectionFacade {

    private final ElectionRepository electionRepository;
    private final SecurityService securityService;


    public ElectionFacade(CommissionLocaleRepository commissionLocaleRepository, ElectionRepository electionRepository, SecurityService securityService) {
        this.electionRepository = electionRepository;
        this.securityService = securityService;
    }

//    @Transactional(readOnly = true)
//    public List<CodeDesignationDto> recupererCommissionLocales(String regionCodes, String circonscriptionCodes) {
//        Set<String> codesCirconscription = StringUtils.isNotBlank(circonscriptionCodes) ?
//                Arrays.stream(circonscriptionCodes.split(","))
//                        .filter(Objects::nonNull)
//                        .collect(toSet()) :
//                Collections.emptySet();
//
//        Set<String> codesRegion = StringUtils.isNotBlank(regionCodes) ?
//                Arrays.stream(regionCodes.split(","))
//                        .filter(Objects::nonNull)
//                        .collect(toSet()) :
//                Collections.emptySet();
//        List<String> codeCommissionLocales = securityService.recupererCodeCommissionLocalesAutorisesPourUtilisateurConnecte();
//        return commissionLocaleRepository.recupererParRegionEtCirconscription(codesRegion, codesCirconscription).stream()
//                .filter(commissionLocale -> codeCommissionLocales.contains(commissionLocale.getCode()))
//                .map(codeCommissionLocale -> new CodeDesignationDto(codeCommissionLocale.getCode(), codeCommissionLocale.getDesignation()))
//                .sorted(Comparator.comparing(CodeDesignationDto::getDesignation))
//                .collect(toList());
//    }

    @Transactional(readOnly = true)
    public List<Long> listerAnnees() {
        return electionRepository.findAll()
                .stream()
                .map(Election::getAnnee)
                .distinct()
                .collect(toList());
    }

    /**
     * Liste tous les scrutins.
     *
     * @return la liste des scrutins.
     */
    @Transactional
    public List<ElectionDto> listerScrutins() {
        return electionRepository.findAll()
                .stream()
                .map(ElectionDto::new)
                .collect(toList());
    }

    public List<CodeDesignationDto> listerTourScrutins(long annee, TypeScrutin typeScrutin) {
        List<CodeDesignationDto> tours = new ArrayList<>();
        if (electionRepository.existsByAnneeAndType(annee, typeScrutin)) {
            tours.add(new CodeDesignationDto(PREMIER_TOUR.name(), PREMIER_TOUR.getDesignation()));

            if (electionRepository.existsByAnneeAndTypeAndTour(annee, typeScrutin, SECOND_TOUR)) {
                tours.add(new CodeDesignationDto(SECOND_TOUR.name(), SECOND_TOUR.getDesignation()));
            }
        }

        return tours;
    }
}
