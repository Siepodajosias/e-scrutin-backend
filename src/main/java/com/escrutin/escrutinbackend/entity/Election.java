package com.escrutin.escrutinbackend.entity;


import com.escrutin.escrutinbackend.enums.StatutScrutin;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;

import javax.persistence.*;

import static com.escrutin.escrutinbackend.enums.JpaConstants.ID;
import static com.escrutin.escrutinbackend.enums.JpaConstants.SEQ;
import static javax.persistence.EnumType.STRING;

@Entity
public class Election extends AbstractEntity {
	public static final String TABLE_NAME = "election";
	public static final String TABLE_ID = TABLE_NAME + ID;
	public static final String TABLE_SEQ = TABLE_ID + SEQ;

	@Id
	@SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
	private Long id;

	@Column(name = "code")
	private String code;

	@Enumerated(STRING)
	@Column(name = "tour")
	private TourScrutin tour;

    @Enumerated(STRING)
    @Column(name = "type")
    private TypeScrutin type;

    @Column(name = "annee")
    private long annee;

	public Election() {
	}

    public Long getId() {
        return id;
    }

	public String getCode() {
		return code;
	}

	public TypeScrutin getType() {
		return type;
	}

    public long getAnnee() {
        return annee;
    }

	public TourScrutin getTour() {
		return tour;
	}
}
