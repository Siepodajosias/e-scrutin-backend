package com.escrutin.escrutinbackend.exception;

public class UtilisateurException extends RuntimeException{

	/**
	 * Constructeur par défaut
	 * @param message le message d'erreur
	 */
	public UtilisateurException(String message) {
		super(message);
	}

	/**
	 * Exception levée lorsque l'utilisateur n'est pas trouvé.
	 *
	 * @param id l'identifiant de l'utilisateur.
	 * @return l'exception
	 */
	public static UtilisateurException utilisateurInexistant(Long id) {
		return new UtilisateurException(String.format("Aucun n'utilisateur trouvé pour l'identifiant %s", id));
	}
}
