package com.escrutin.escrutinbackend.enums;

public enum GeometryType {
	POINT("Point");

	private String libelle;

	GeometryType(String libelle) {
	}

	public String getLibelle() {
		return libelle;
	}
}
