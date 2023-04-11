package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.enums.TypeNotification;

public class NotificationMessage {

	private TypeNotification type;
	private String nom;
	private CodeDesignationDto circonscription;
	private CodeDesignationDto region;
	private CodeDesignationDto commissionLocale;
	private CodeDesignationDto commune;

	public NotificationMessage() {
	}

	public NotificationMessage(TypeNotification type, CodeDesignationDto region, CodeDesignationDto circonscription,
							   CodeDesignationDto commissionLocale, CodeDesignationDto commune) {
		this.type = type;
		this.region = region;
		this.circonscription = circonscription;
		this.commissionLocale = commissionLocale;
		this.commune = commune;
	}

	public TypeNotification getType() {
		return type;
	}

	public String getNom() {
		return nom;
	}

	public CodeDesignationDto getCirconscription() {
		return circonscription;
	}

	public CodeDesignationDto getRegion() {
		return region;
	}

	public CodeDesignationDto getCommissionLocale() {
		return commissionLocale;
	}

	public CodeDesignationDto getCommune() {
		return commune;
	}
}
