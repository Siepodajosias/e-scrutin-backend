package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Zone;

public class ZoneDto extends CodeDesignationDto {

    private long id;

    public ZoneDto(String code, String designation) {
        super(code, designation);
    }

    public ZoneDto(Zone zone) {
        super(zone.getCode(), zone.getDesignation());
        this.id = zone.getId();
    }

    public ZoneDto(String code, String designation, long id) {
        super(code, designation);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
