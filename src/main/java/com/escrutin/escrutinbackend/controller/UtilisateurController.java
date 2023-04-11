package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.UtilisateurDto;
import com.escrutin.escrutinbackend.facade.UtilisateurFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateur")
@SecurityRequirement(name = "Authorization")
public class UtilisateurController {

	private final UtilisateurFacade utilisateurFacade;

	public UtilisateurController(UtilisateurFacade utilisateurFacade) {
		this.utilisateurFacade = utilisateurFacade;
	}

	/**
	 * Liste tous les utilisateurs.
	 *
	 * @return la liste {@link UtilisateurDto} des utilisateurs.
	 */
	@GetMapping("/lister")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<UtilisateurDto> listerUtilisateurs() {
		return utilisateurFacade.listerUtilisateurs();
	}

	/**
	 * Crée ou modifie un utilisateur.
	 *
	 * @param utilisateurDto l'utilisateur.
	 */
	@PostMapping("/creer")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void creerOuModifierUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
		utilisateurFacade.creerOuModifierUtilisateur(utilisateurDto);
	}

	/**
	 * Désactive un utilisateur.
	 *
	 * @param utilisateurDto l'utilisateur.
	 */
	@PostMapping("/activer-ou-desactiver")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void desactiverUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
		utilisateurFacade.activerOuDesactiverUtilisateur(utilisateurDto);
	}

	/**
	 * Modifie le mot de passe d'un utilisateur.
	 *
	 * @param utilisateurDto l'utilisateur.
	 */
	@PostMapping("/modifier-mot-de-passe")
	public void modifierMotDePasseUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
		utilisateurFacade.modifierMotDePasse(utilisateurDto);
	}

	/**
	 * Supprime un utilisateur.
	 *
	 * @param id l'identifiant de l'utilisateur.
	 */
	@DeleteMapping("/supprimer/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void supprimerUtilisateur(@PathVariable("id") Long id) {
		utilisateurFacade.supprimerUtilisateur(id);
	}
}
