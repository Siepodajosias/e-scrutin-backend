package com.escrutin.escrutinbackend.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static java.util.Objects.nonNull;

public class SecurityUtils {

	public static String getLoginUtilisateur() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (nonNull(context) && nonNull(context.getAuthentication())) {
			UserDetails principal = (UserDetails) context.getAuthentication().getPrincipal();
			return  principal.getUsername();
		}
		throw new RuntimeException("Authentification échoué");
	}
}
