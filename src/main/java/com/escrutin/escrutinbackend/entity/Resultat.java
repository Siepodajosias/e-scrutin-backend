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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = Resultat.TABLE_NAME)
public class Resultat extends AbstractEntity {

    public static final String TABLE_NAME = "resultat";
    public static final String TABLE_ID = TABLE_NAME + ID;
    public static final String TABLE_SEQ = TABLE_ID + SEQ;

    @Id
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = BureauVote.TABLE_ID, nullable = false)
    private BureauVote bureauVote;

    @Column(name = "nombre_votants")
    private long nombreVotants;

    @Column(name = "nombre_inscrits")
    private long nombreInscrits;

    @Column(name = "nombre_hommes_votants")
    private long nombreHommesVotants;

    @Column(name = "nombre_femmes_votantes")
    private long nombreFemmesVotantes;

    @Column(name = "nombre_bulletins_nuls")
    private long nombreBulletinsNuls;

    @Column(name = "nombre_bulletins_blancs")
    private long nombreBulletinsBlancs;

    @Column(name = "suffrages_exprimes")
    private long suffragesExprimes;

    @Column(name = "heure_reception")
    private String heureReception;

    @Column(name = "taux_participation")
    private BigDecimal tauxParticipation;

    @ManyToOne
    @JoinColumn(name = Election.TABLE_ID, nullable = false)
    private Election election;

    // Represente les resultats par candidat
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resultat")
    private final Set<JoinResultatCandidatElection> joinResultatCandidatElections = new HashSet<>();

    public Resultat() {
    }

    /**
     * Retourne le code du lieu de vote
     * @return le code du lieu de vote
     */
    public String codeLieuVote() {
        return lieuVote().getCode();
    }

    /**
     * Retourne la désignation du lieu de vote
     * @return la désignation du lieu de vote
     */
    public String designationLieuVote() {
        return lieuVote().getDesignation();
    }

    /**
     * Retourne le code reduit du bureau de vote
     * @return le code réduit du bureau de vote
     */
    public String codeBureauVoteReduit() {
        return getBureauVote().getCode().replace(codeLieuVote(), "");
    }

    /**
     * Retourne la sous prefecture
     * @return la sous prefecture
     */
    public Zone sousPrefecture() {
        return getBureauVote().sousPrefecture();
    }

    /**
     * Retourne le département
     * @return le departement
     */
    public Zone departement() {
        return getBureauVote().departement();
    }

    /**
     * Retourne le code de la région
     *
     * @return le code de la région
     */
    public String codeRegion() {
        return getBureauVote().codeRegion();
    }

    /**
     * Retourne le lieu de vote
     *
     * @return le lieu de vote
     */
    public LieuVote lieuVote() {
        return getBureauVote().getLieuVote();
    }

    /**
     * Retourne la commune
     * @return la commune
     */
    public Zone commune() {
        return getBureauVote().commune();
    }

    /**
     * Retourne la circonscription
     *
     * @return la circonscription
     */
    public Circonscription circonscription() {
        return commissionLocale().getCirconscription();
    }

    /**
     * Retourne la commission locale
     *
     * @return la commission locale
     */
    public CommissionLocale commissionLocale() {
        return lieuVote().getCommissionLocale();
    }

    /**
     * Retourne la région
     *
     * @return la région
     */
    public Zone region() {
        return getBureauVote().region();
    }

    public Long getId() {
        return id;
    }

    public BureauVote getBureauVote() {
        return bureauVote;
    }

    public long getNombreVotants() {
        return nombreVotants;
    }

    public long getNombreBulletinsNuls() {
        return nombreBulletinsNuls;
    }

    public long getNombreBulletinsBlancs() {
        return nombreBulletinsBlancs;
    }

    public long getSuffragesExprimes() {
        return suffragesExprimes;
    }

    public String getHeureReception() {
        return heureReception;
    }

    public Set<JoinResultatCandidatElection> getJoinResultatCandidats() {
        return joinResultatCandidatElections;
    }

    public BigDecimal getTauxParticipation() {
        return tauxParticipation;
    }

    public long getNombreInscrits() {
        return nombreInscrits;
    }

    public long getNombreHommesVotants() {
        return nombreHommesVotants;
    }

    public long getNombreFemmesVotantes() {
        return nombreFemmesVotantes;
    }

    /**
     * Retourne le code de la commune
     * @return le code de la commune
     */
    public String codeCommune() {
        return getBureauVote().codeCommune();
    }

    /**
     * Retourne le code de la sous prefecture
     *
     * @return le code de la sous prefecture
     */
    public String codeSousPrefecture() {
        return getBureauVote().codeSousPrefecture();
    }

    /**
     * Retourne le code du département
     *
     * @return le code du departement
     */
    public String codeDepartement() {
        return getBureauVote().codeDepartement();
    }

    public Election getElection() {
        return election;
    }

    public Set<JoinResultatCandidatElection> getJoinResultatCandidatElections() {
        return joinResultatCandidatElections;
    }
}
