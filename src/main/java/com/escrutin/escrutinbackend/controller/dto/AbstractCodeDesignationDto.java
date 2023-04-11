package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.AbstractCodeDesignationEntity;

public abstract class AbstractCodeDesignationDto extends AbstractDto {
	private String code;
	private String designation;

	protected AbstractCodeDesignationDto() {

	}

	protected AbstractCodeDesignationDto(String code, String designation) {
		this.code = code;
		this.designation = designation;
	}

	protected <T extends AbstractCodeDesignationEntity> AbstractCodeDesignationDto(T entity){
		this(entity.getCode(), entity.getDesignation());
	}

	public String getCode() {
		return code;
	}

	public String getDesignation() {
		return designation;
	}
}
