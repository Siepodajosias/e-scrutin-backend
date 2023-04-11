package com.escrutin.escrutinbackend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = TypeZone.TABLE_NAME)
public class TypeZone extends AbstractCodeDesignationEntity{

    public static final String TABLE_NAME = "type_zone";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    public static final String REGION = "REGION";
    public static final String COMMUNE = "COMMUNE";
    public static final String SOUS_PREFECTURE = "SOUS_PREFECTURE";
    public static final String DEPARTEMENT = "DEPARTEMENT";

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    public TypeZone() {
    }

    public Long getId() {
        return id;
    }
}
