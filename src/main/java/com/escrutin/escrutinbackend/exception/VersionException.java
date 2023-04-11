package com.escrutin.escrutinbackend.exception;

public class VersionException extends RuntimeException{

	public VersionException(String message) {
		super(message);
	}

	/**
	 * Exception levée lorsqu'une erreur quelconque a été rencontrée pendant la récupération des version.
	 */
	public static VersionException erreurDeRecuperationDeVersion() {
		return new VersionException("Une erreur a été rencontrée lors de la récupération de la version");
	}
}
