package com.escrutin.escrutinbackend.enums;

public enum TourScrutin {
	PREMIER_TOUR("1er Tour"),
	SECOND_TOUR("2ème Tour");

	private String designation;

	TourScrutin(String designation) {
		this.designation = designation;
	}

	public String getDesignation() {
		return designation;
	}
}
