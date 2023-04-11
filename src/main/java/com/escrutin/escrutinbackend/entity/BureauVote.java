package com.escrutin.escrutinbackend.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;

@Entity
@Table(name = BureauVote.TABLE_NAME)
public class BureauVote extends AbstractCodeDesignationEntity {
	public static final String TABLE_NAME = "bureau_vote";
	public static final String TABLE_ID = TABLE_NAME + ID;
	public static final String TABLE_SEQ = TABLE_ID + SEQ;

	@Id
	@SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = LieuVote.TABLE_ID)
	private LieuVote lieuVote;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = Zone.TABLE_ID)
	private Zone zone;

    public BureauVote() {
        super();
    }

	/**
	 * Retourne le login du responsable du CEL
	 * @return le login du responsable du CEL
	 */
	public String loginResponsable() {
		return commissionLocale().getLoginResponsable();
	}

	/**
	 * Retourne le code de la circonscription
	 * @return le code de la circonscription
	 */
	public String codeCirconscription() {
		return circonscription().getCode();
	}

	/**
	 * Retourne le code de la commission locale
	 * @return le code de la commission locale
	 */
	public String codeCommissionLocale() {
		return commissionLocale().getCode();
	}

	/**
	 * Retourne la commission locale du bureau de vote
	 *
	 * @return la commission locale du bureau de vote
	 */
	public CommissionLocale commissionLocale() {
		return getLieuVote().getCommissionLocale();
	}

	/**
	 * Retourne le code de la région
	 * @return le code de la région
	 */
	public String codeRegion() {
		return region() != null ? region().getCode() : null;
	}

	/**
	 * Retourne la région du bureau de vote
	 * @return la région
	 */
    public Zone region() {

		if (departement() != null) {
			return departement().getParent();
		}
		else if (getZone() != null && getZone().codeType().equals(TypeZone.REGION)) {
			return getZone();
		}

        return null;
    }

	/**
	 * Retourne la circonscription du bureau de vote
	 * @return la circonscription
	 */
	public Circonscription circonscription() {
		return commissionLocale().getCirconscription();
	}

	/**
	 * Retourne le code de la sous préfecture
	 * @return le code de la sous prefecture
	 */
	public String codeSousPrefecture() {
		return sousPrefecture() != null ? sousPrefecture().getCode() : null;
	}

	/**
	 * Retourne la sous prefecture
	 * @return la sous prefecture
	 */
	public Zone sousPrefecture() {
		if (getZone() != null && getZone().codeType().equals(TypeZone.COMMUNE)) {
			return getZone().getParent();
		}
		else if (getZone() != null && getZone().codeType().equals(TypeZone.SOUS_PREFECTURE)){
			return getZone();
		}
		return null;
	}

	/**
	 * Retourne le code du département
	 *
	 * @return le code du departement
	 */
	public String codeDepartement() {
		return departement() != null ? departement().getCode() : null;
	}

	/**
	 * Retourne le type de la zone
	 * @return le type de la zone
	 */
	public TypeZone typeZone() {
		return getZone().getType();
	}

	/**
	 * Retourne le code de la zone
	 *
	 * @return le code de la zone
	 */
	public String codeCommune() {
		return typeZone().getCode().equals(TypeZone.COMMUNE) ? getZone().getCode() : null;
	}

	/**
	 * Retourne le département
	 * @return le département
	 */
	public Zone departement() {

		if (sousPrefecture() != null) {
			return sousPrefecture().getParent();
		}
		else if (getZone() != null && getZone().codeType().equals(TypeZone.DEPARTEMENT)) {
			return getZone();
		}
		return null;
	}

    public Long getId() {
        return id;
    }

    public LieuVote getLieuVote() {
        return lieuVote;
    }

	public Zone getZone() {
		return zone;
	}

	public Zone commune() {
		return typeZone().getCode().equals(TypeZone.COMMUNE) ? getZone() : null;
	}
}
