package com.escrutin.escrutinbackend.facade;

import com.escrutin.escrutinbackend.controller.dto.UtilisateurDto;
import com.escrutin.escrutinbackend.entity.Utilisateur;
import com.escrutin.escrutinbackend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.escrutin.escrutinbackend.exception.UtilisateurException.utilisateurInexistant;
import static com.escrutin.escrutinbackend.service.SecurityService.crypterPassword;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class UtilisateurFacade {

	private final UtilisateurRepository utilisateurRepository;

	public UtilisateurFacade(UtilisateurRepository utilisateurRepository) {
		this.utilisateurRepository = utilisateurRepository;
	}

	/**
	 * Liste tous les utilisateurs.
	 *
	 * @return la liste {@link UtilisateurDto} des utilisateurs.
	 */
	@Transactional(readOnly = true)
	public List<UtilisateurDto> listerUtilisateurs() {
		return utilisateurRepository.findAll()
				.stream()
				.map(UtilisateurDto::new)
				.sorted(comparing(UtilisateurDto::getNom, String.CASE_INSENSITIVE_ORDER)
						.thenComparing(UtilisateurDto::getPrenoms, String.CASE_INSENSITIVE_ORDER))
				.collect(toList());
	}

	/**
	 * Crée ou modifie un utilisateur.
	 *
	 * @param utilisateurDto l'utilisateur.
	 */
	@Transactional
	public void creerOuModifierUtilisateur(UtilisateurDto utilisateurDto) {
		Utilisateur utilisateur = this.utilisateurRepository.findById(utilisateurDto.getId()).orElse(new Utilisateur());
		utilisateur.mettreAJourUtilisateur(
				utilisateurDto.getUsername(),
				crypterPassword(utilisateurDto.getPassword()),
				utilisateurDto.getNom(),
				utilisateurDto.getPrenoms(),
				utilisateurDto.getRole(),
				utilisateurDto.getStatut()
		);
		this.utilisateurRepository.save(utilisateur);
	}

	/**
	 * Modifie le mot de passe d'un utilisateur.
	 *
	 * @param utilisateurDto l'utilisateur.
	 */
	@Transactional
	public void modifierMotDePasse(UtilisateurDto utilisateurDto) {
		Utilisateur utilisateur = this.utilisateurRepository.findById(utilisateurDto.getId())
				.orElseThrow(() -> utilisateurInexistant(utilisateurDto.getId()));
		utilisateur.setPassword(crypterPassword(utilisateurDto.getPassword()));
		utilisateur.setStatut(utilisateurDto.getStatut());
	}

	/**
	 * Active/Désactive un utilisateur.
	 *
	 * @param utilisateurDto l'utilisateur.
	 */
	@Transactional
	public void activerOuDesactiverUtilisateur(UtilisateurDto utilisateurDto) {
		Utilisateur utilisateur = this.utilisateurRepository.findById(utilisateurDto.getId())
				.orElseThrow(() -> utilisateurInexistant(utilisateurDto.getId()));
		utilisateur.setPassword(crypterPassword("azerty"));
		utilisateur.setStatut(utilisateurDto.getStatut());
	}

	/**
	 * Supprime un utilisateur.
	 *
	 * @param id l'identifiant de l'utilisateur.
	 */
	@Transactional
	public void supprimerUtilisateur(Long id) {
		Utilisateur utilisateur = this.utilisateurRepository.findById(id)
				.orElseThrow(() -> utilisateurInexistant(id));
		utilisateurRepository.delete(utilisateur);
	}
}
