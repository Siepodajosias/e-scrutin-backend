package com.escrutin.escrutinbackend.entity;


import com.escrutin.escrutinbackend.enums.TourScrutin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = JoinResultatCandidatElection.TABLE_NAME)
public class JoinResultatCandidatElection extends AbstractEntity {

    public static final String TABLE_NAME = "join_resultat_candidat_election";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Candidat.TABLE_ID, nullable = false)
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = Resultat.TABLE_ID, nullable = false)
    private Resultat resultat;

    @Column(name = "voix")
    private long voix;

    public JoinResultatCandidatElection() {
    }

    /**
     * Retourne l'ordre d'affichage du candidat
     *
     * @return l'ordre de candidature
     */
    public int ordreCandidat() {
        return getCandidat().getOrdre();
    }

    /**
     * Récupère le nom du candidat
     *
     * @return le nom du candidat
     */
    public String nomCandidat() {
        return getCandidat().getNom();
    }

    /**
     * Retourne le code de la région
     *
     * @return le code de la région
     */
    public String codeRegion() {
        return getResultat().codeRegion();
    }

    /**
     * Retourne la sous prefecture
     * @return la sous prefecture
     */
    public Zone sousPrefecture() {
        return getResultat().sousPrefecture();
    }

    /**
     * Retourne le bureau de vote
     *
     * @return le bureau de vote
     */
    public BureauVote bureauVote() {
        return getResultat().getBureauVote();
    }

    /**
     * Retourne la région
     * @return la région
     */
    public Zone region() {
        return getResultat().region();
    }

    /**
     * Retourne le departement
     * @return le departement
     */
    public Zone departement() {
        return getResultat().departement();
    }

    /**
     * Retourne le code de la commune
     * @return le code de la commune
     */
    public String codeCommune() {
        return getResultat().codeCommune();
    }

    /**
     * Retourne le code de la sous prefecture
     * @return le code de la sous prefecture
     */
    public String codeSousPrefecture() {
        return getResultat().codeSousPrefecture();
    }

    /**
     * Retourne le code du département
     * @return le code du département
     */
    public String codeDepartement() {
        return getResultat().codeDepartement();
    }


    /**
     * Retourne la circonscription
     *
     * @return la ciro
     */
    public Circonscription circonscription() {
        return bureauVote().circonscription();
    }

    /**
     * Retourne la commission locale
     *
     * @return la commission locale
     */
    public CommissionLocale commissionLocale() {
        return bureauVote().commissionLocale();
    }

    /**
     * Retourne le lieu de vote
     *
     * @return le lieu de vote
     */
    public LieuVote lieuVote() {
        return bureauVote().getLieuVote();
    }

    /**
     * Retourne le parti politique
     *
     * @return le parti politique
     */
    public PartiPolitique partiPolitique() {
        return getCandidat().getPartiPolitique();
    }
    /**
     * Récupère le nom du parti politique
     *
     * @return le nom du parti politique
     */
    public String nomParti() {
        return getCandidat().nomPartiPolitique();
    }

    /**
     * Retourne le code couleur du parti
     *
     * @return le code couleur du parti
     */
    public String codeCouleurParti() {
        return getCandidat().codeCouleur();
    }

    /**
     * Retourne la commune
     * @return la commune
     */
    public Zone commune() {
        return getResultat().commune();
    }

    public Long getId() {
        return id;
    }

    public Resultat getResultat() {
        return resultat;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public long getVoix() {
        return voix;
    }


}
