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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = Zone.TABLE_NAME)
public class Zone extends AbstractCodeDesignationEntity {

    public static final String TABLE_NAME = "zone";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @ManyToOne
    @JoinColumn(name = TypeZone.TABLE_ID, nullable = false)
    private TypeZone type;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Zone.TABLE_ID, nullable = false)
    private Zone parent;

    @Column(name = "login_superviseur")
    private String loginSuperviseur;

    public Zone() {
    }

    /**
     * Le code du type de la zone
     *
     * @return le code du type
     */
    public String codeType() {
        return getType().getCode();
    }

    /**
     * Le code de la zone parent
     *
     * @return le code de la zone parent
     */
    public String codeZoneParent() {
        return getParent() != null ? getParent().getCode() : null;
    }

    public Long getId() {
        return id;
    }

    public TypeZone getType() {
        return type;
    }

    public Zone getParent() {
        return parent;
    }

    public String getLoginSuperviseur() {
        return loginSuperviseur;
    }
}
