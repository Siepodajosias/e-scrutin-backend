package com.escrutin.escrutinbackend.entity;

import javax.persistence.*;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = Candidat.TABLE_NAME)
public class Candidat extends AbstractEntity {

    public static final String TABLE_NAME = "candidat";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "ordre")
    private int ordre;

    @ManyToOne
    @JoinColumn(name = PartiPolitique.TABLE_ID, nullable = false)
    private PartiPolitique partiPolitique;

    public Candidat() {
    }

    /**
     * Retourne le nom du parti politique
     *
     * @return le nom du parti
     */
    public String nomPartiPolitique() {
        return getPartiPolitique().getNom();
    }

    /**
     * Retourne le code couleur du parti
     *
     * @return le code couleur
     */
    public String codeCouleur() {
        return getPartiPolitique().getCodeCouleur();
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public PartiPolitique getPartiPolitique() {
        return partiPolitique;
    }

    public int getOrdre() {
        return ordre;
    }
}
