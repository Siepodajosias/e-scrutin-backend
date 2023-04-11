package com.escrutin.escrutinbackend.entity;

import com.escrutin.escrutinbackend.security.SecurityUtils;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {
	private String createBy;

	private LocalDateTime createAt;
	private String updateBy;

	private LocalDateTime updateAt;

	protected String adresseMac;

	@Version
	private long version;

	public String getCreateBy() {
		return createBy;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public String getAdresseMac() {
		return adresseMac;
	}

	@PreUpdate
	public void beforeUpdate() {
		updateAt = LocalDateTime.now();
		updateBy = SecurityUtils.getLoginUtilisateur();
	}

	@PrePersist
	public void beforeInsert() {
		createAt = LocalDateTime.now();
		createBy = SecurityUtils.getLoginUtilisateur();
	}
}
