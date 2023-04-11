package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;

public class CodeDesignationDto extends AbstractCodeDesignationDto {
    private long id;

    public CodeDesignationDto(String code, String designation) {
        super(code, designation);
    }

    public long getId() {
        return id;
    }
}
