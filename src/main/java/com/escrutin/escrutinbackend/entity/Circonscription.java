package com.escrutin.escrutinbackend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = Circonscription.TABLE_NAME)
public class Circonscription extends AbstractCodeDesignationEntity {

    public static final String TABLE_NAME = "circonscription";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "circonscription")
    private Set<CommissionLocale> commissionLocales = new HashSet<>();

    public Circonscription() {
    }

    public Circonscription(String code, String designation) {
        super(code, designation);
    }


    public Long getId() {
        return id;
    }

    public Set<CommissionLocale> getCommissionLocales() {
        return commissionLocales;
    }
}
