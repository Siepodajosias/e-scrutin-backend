package com.escrutin.escrutinbackend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = CommissionLocale.TABLE_NAME)
public class CommissionLocale extends AbstractCodeDesignationEntity {

    public static final String TABLE_NAME = "commission_locale";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Circonscription.TABLE_ID)
    private Circonscription circonscription;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commissionLocale")
    private Set<LieuVote> lieuVotes = new HashSet<>();

    @Column(name = "login_responsable")
    private String loginResponsable;

    public CommissionLocale() {
    }

    public CommissionLocale(String code, String designation, Circonscription circonscription) {
        super(code, designation);
        this.circonscription = circonscription;
    }

    public String codeCirconscription() {
        return getCirconscription().getCode();
    }

    public Long getId() {
        return id;
    }

    public Circonscription getCirconscription() {
        return circonscription;
    }

    public Set<LieuVote> getLieuVotes() {
        return lieuVotes;
    }

    public String getLoginResponsable() {
        return loginResponsable;
    }
}
