package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.CodeDesignationDto;
import com.escrutin.escrutinbackend.controller.dto.CommissionLocaleDto;
import com.escrutin.escrutinbackend.controller.dto.NotificationMessage;
import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.enums.TypeNotification;
import com.escrutin.escrutinbackend.repository.BureauVoteRepository;
import com.escrutin.escrutinbackend.repository.CommissionLocaleRepository;
import com.escrutin.escrutinbackend.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class CommissionLocaleFacade {

	private final BureauVoteRepository bureauVoteRepository;
	private final CommissionLocaleRepository commissionLocaleRepository;
	private final SecurityService securityService;


	public CommissionLocaleFacade(CommissionLocaleRepository commissionLocaleRepository,
								  SecurityService securityService,
								  BureauVoteRepository bureauVoteRepository) {
		this.commissionLocaleRepository = commissionLocaleRepository;
		this.securityService = securityService;
		this.bureauVoteRepository = bureauVoteRepository;
	}

	@Transactional(readOnly = true)
	public List<CommissionLocaleDto> recupererCommissionLocales(String regionCodes, String circonscriptionCodes) {
		Set<String> codesCirconscription = StringUtils.isNotBlank(circonscriptionCodes) ?
				Arrays.stream(circonscriptionCodes.split(","))
						.filter(Objects::nonNull)
						.collect(toSet()) :
				Collections.emptySet();
		List<String> codeCommissionLocales = securityService.recupererCodeCommissionLocalesAutorisesPourUtilisateurConnecte();
		return commissionLocaleRepository.recupererParCirconscription(codesCirconscription).stream()
				.filter(commissionLocale -> codeCommissionLocales.contains(commissionLocale.getCode()))
				.map(CommissionLocaleDto::new)
				.sorted(Comparator.comparing(CommissionLocaleDto::getDesignation))
				.collect(toList());
	}

	@Transactional(readOnly = true)
	public NotificationMessage recupererInformations(String codeCommissionLocale) {
		BureauVote bureauVote = bureauVoteRepository.recupererParCommissionLocale(codeCommissionLocale);
		return new NotificationMessage(
				TypeNotification.LECTURE_FICHIER,
				new CodeDesignationDto(bureauVote.codeRegion(), bureauVote.region().getDesignation()),
				new CodeDesignationDto(bureauVote.codeCirconscription(), bureauVote.circonscription().getDesignation()),
				new CodeDesignationDto(bureauVote.codeCommissionLocale(), bureauVote.commissionLocale().getDesignation()),
				new CodeDesignationDto(bureauVote.codeCommune(), bureauVote.commune().getDesignation())
		);
	}
}
