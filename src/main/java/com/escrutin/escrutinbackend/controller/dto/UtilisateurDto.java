package com.escrutin.escrutinbackend.controller.dto;

import com.escrutin.escrutinbackend.entity.Utilisateur;
import com.escrutin.escrutinbackend.enums.Role;
import com.escrutin.escrutinbackend.enums.StatutUtilisateur;

public class UtilisateurDto {

	private long id;
	private String nom;
	private String prenoms;
	private String username;
	private String password;
	private Role role;
	private StatutUtilisateur statut;

	public UtilisateurDto() {
	}

	public UtilisateurDto(Utilisateur utilisateur) {
		this.id = utilisateur.getId();
		this.nom = utilisateur.getNom();
		this.prenoms = utilisateur.getPrenoms();
		this.username = utilisateur.getUsername();
		this.role = utilisateur.getRole();
		this.statut = utilisateur.getStatut();
	}

	public long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public StatutUtilisateur getStatut() {
		return statut;
	}
}
