package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.TokenDto;
import com.escrutin.escrutinbackend.service.SecurityService;
import com.escrutin.escrutinbackend.controller.dto.AuthDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/securite")
public class SecurityController {

	private final SecurityService securityService;

	public SecurityController(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Permet d'authentifier l'utilisateur.
	 *
	 * @param authDto les informations de connexion.
	 * @return le token d'authentification
	 */
	@PostMapping("/auth")
	public TokenDto auth(@RequestBody AuthDto authDto) {
		return securityService.autentifierUtilisateur(authDto);
	}
}
