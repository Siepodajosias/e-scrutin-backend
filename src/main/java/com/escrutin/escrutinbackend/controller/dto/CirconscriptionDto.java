package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.Zone;

public class CirconscriptionDto extends CodeDesignationDto {

    private long id;

    public CirconscriptionDto(String code, String designation) {
        super(code, designation);
    }

    public CirconscriptionDto(Circonscription circonscription) {
        super(circonscription.getCode(), circonscription.getDesignation());
        this.id = circonscription.getId();
    }

    public CirconscriptionDto(String code, String designation, long id) {
        super(code, designation);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
