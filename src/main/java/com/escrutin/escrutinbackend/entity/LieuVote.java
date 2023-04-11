package com.escrutin.escrutinbackend.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.Optional;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
public class LieuVote extends AbstractCodeDesignationEntity {
    public static final String TABLE_NAME = "lieu_vote";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = CommissionLocale.TABLE_ID)
    private CommissionLocale commissionLocale;

    public LieuVote() {
    }

    public Long getId() {
        return id;
    }

    public CommissionLocale getCommissionLocale() {
        return commissionLocale;
    }


    public String codeCommissionnLocale() {
        return Optional.ofNullable(commissionLocale)
                .map(CommissionLocale::getCode)
                .orElse(null);
    }

    public String designationCommissionLocale() {
        return Optional.ofNullable(commissionLocale)
                .map(CommissionLocale::getDesignation)
                .orElse(null);
    }
}
