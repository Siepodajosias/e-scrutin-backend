package com.escrutin.escrutinbackend.auth;

import com.escrutin.escrutinbackend.entity.Utilisateur;
import com.escrutin.escrutinbackend.enums.Role;
import org.springframework.security.core.userdetails.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilisateurMockBuilder {
	private Long id;
	private String username;
	private String password;
	private String nom;
	private String prenoms;
	private Role role;
	private User user;

	public UtilisateurMockBuilder() {
	}

	public Utilisateur build() {
		Utilisateur utilisateur = mock(Utilisateur.class);

		when(utilisateur.getId()).thenReturn(id);
		when(utilisateur.getNom()).thenReturn(nom);
		when(utilisateur.getPrenoms()).thenReturn(prenoms);
		when(utilisateur.getUsername()).thenReturn(username);
		when(utilisateur.getPassword()).thenReturn(password);
		when(utilisateur.getRole()).thenReturn(role);
		when(utilisateur.buildUser()).thenReturn(user);
		return utilisateur;
	}

	public UtilisateurMockBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	public UtilisateurMockBuilder setNom(String nom) {
		this.nom = nom;
		return this;
	}

	public UtilisateurMockBuilder setPrenoms(String prenoms) {
		this.prenoms = prenoms;
		return this;
	}

	public UtilisateurMockBuilder setRole(Role role) {
		this.role = role;
		return this;
	}

	public UtilisateurMockBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	public UtilisateurMockBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public UtilisateurMockBuilder setUser(User user) {
		this.user = user;
		return this;
	}
}
