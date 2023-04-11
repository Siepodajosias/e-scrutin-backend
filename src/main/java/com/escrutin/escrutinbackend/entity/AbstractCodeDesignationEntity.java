package com.escrutin.escrutinbackend.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractCodeDesignationEntity extends AbstractEntity {

	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "designation", nullable = false)
	private String designation;

	public AbstractCodeDesignationEntity(){

	}
	protected AbstractCodeDesignationEntity(String code, String designation) {
		this.code = code;
		this.designation = designation;
	}

	public String getCode() {
		return code;
	}

	public String getDesignation() {
		return designation;
	}

	protected boolean equalsOnOtherFields(AbstractEntity other) {
		return Objects.equals(getCode(), ((AbstractCodeDesignationEntity) other).getCode());
	}
}
