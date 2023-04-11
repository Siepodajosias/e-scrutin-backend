package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.Zone;

public class CommissionLocaleDto extends CodeDesignationDto {

    private long id;

    public CommissionLocaleDto(String code, String designation) {
        super(code, designation);
    }

    public CommissionLocaleDto(CommissionLocale commissionLocale) {
        super(commissionLocale.getCode(), commissionLocale.getDesignation());
        this.id = commissionLocale.getId();
    }

    public CommissionLocaleDto(String code, String designation, long id) {
        super(code, designation);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
