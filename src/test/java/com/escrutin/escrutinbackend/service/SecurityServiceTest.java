package com.escrutin.escrutinbackend.service;

import com.escrutin.escrutinbackend.auth.AuthDtoMockBuilder;
import com.escrutin.escrutinbackend.auth.UtilisateurMockBuilder;
import com.escrutin.escrutinbackend.controller.dto.AuthDto;
import com.escrutin.escrutinbackend.controller.dto.TokenDto;
import com.escrutin.escrutinbackend.entity.Utilisateur;
import com.escrutin.escrutinbackend.repository.BureauVoteRepository;
import com.escrutin.escrutinbackend.repository.CirconscriptionRepository;
import com.escrutin.escrutinbackend.repository.CommissionLocaleRepository;
import com.escrutin.escrutinbackend.repository.UtilisateurRepository;
import com.escrutin.escrutinbackend.repository.ZoneRepository;
import com.escrutin.escrutinbackend.security.JwtTokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.escrutin.escrutinbackend.enums.Role.ADMIN;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SecurityServiceTest {

	@Mock
	private JwtTokenUtils jwtTokenUtils;
	@Mock
	private UtilisateurRepository utilisateurRepository;
	@Mock
	private ZoneRepository zoneRepository;
	@Mock
	private CommissionLocaleRepository commissionLocaleRepository;
	@Mock
	CirconscriptionRepository circonscriptionRepository;

	@Mock
	private BureauVoteRepository bureauVoteRepository;
	private SecurityService securityService;

	AuthDto authDto;
	Utilisateur utilisateur;
	User user;

	@BeforeEach
	void setUp() {
		this.securityService = new SecurityService(jwtTokenUtils, utilisateurRepository, zoneRepository,
				commissionLocaleRepository, circonscriptionRepository, bureauVoteRepository);

		authDto = new AuthDtoMockBuilder()
				.setUsername("username")
				.setPassword("azerty")
				.build();

		utilisateur = new UtilisateurMockBuilder()
				.setId(1L)
				.setNom("nom")
				.setPrenoms("prenoms")
				.setUsername("username")
				.setPassword("$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi")
				.setRole(ADMIN)
				.setUser(user)
				.build();
	}

	@Test
	void shouldRetournerToken_whenAutentifierUtilisateur() {
		// GIVEN
		when(utilisateurRepository.findByUsername("username")).thenReturn(Optional.of(utilisateur));
		when(jwtTokenUtils.generateToken(utilisateur)).thenReturn("azertyuiopqsdfghjklmwxcvbn12345667890");

		// WHEN
		TokenDto token = securityService.autentifierUtilisateur(authDto);

		// THEN
		assertThat(token)
				.isNotNull()
				.extracting(TokenDto::getToken)
				.isEqualTo("Bearer azertyuiopqsdfghjklmwxcvbn12345667890");
	}
}
