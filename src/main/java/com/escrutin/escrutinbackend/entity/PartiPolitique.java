package com.escrutin.escrutinbackend.entity;

import javax.persistence.*;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = PartiPolitique.TABLE_NAME)
public class PartiPolitique extends AbstractEntity{

    public static final String TABLE_NAME = "parti_politique";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "code_couleur")
    private String codeCouleur;

    public PartiPolitique() {
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }
}
