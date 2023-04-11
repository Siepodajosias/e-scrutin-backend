package com.escrutin.escrutinbackend.service;


import com.escrutin.escrutinbackend.controller.dto.AuthDto;
import com.escrutin.escrutinbackend.controller.dto.TokenDto;
import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.Utilisateur;
import com.escrutin.escrutinbackend.entity.Zone;
import com.escrutin.escrutinbackend.enums.Role;
import com.escrutin.escrutinbackend.repository.BureauVoteRepository;
import com.escrutin.escrutinbackend.repository.CirconscriptionRepository;
import com.escrutin.escrutinbackend.repository.CommissionLocaleRepository;
import com.escrutin.escrutinbackend.repository.UtilisateurRepository;
import com.escrutin.escrutinbackend.repository.ZoneRepository;
import com.escrutin.escrutinbackend.security.JwtTokenUtils;
import com.escrutin.escrutinbackend.security.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.escrutin.escrutinbackend.entity.TypeZone.REGION;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class SecurityService implements UserDetailsService {

    private final JwtTokenUtils jwtTokenUtils;
    private final UtilisateurRepository utilisateurRepository;

    private final ZoneRepository zoneRepository;

    private final CirconscriptionRepository circonscriptionRepository;

    private final CommissionLocaleRepository commissionLocaleRepository;

	private final BureauVoteRepository bureauVoteRepository;

    public SecurityService(JwtTokenUtils jwtTokenUtils, UtilisateurRepository utilisateurRepository,
                           ZoneRepository zoneRepository, CommissionLocaleRepository commissionLocaleRepository,
                           CirconscriptionRepository circonscriptionRepository, BureauVoteRepository bureauVoteRepository) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.utilisateurRepository = utilisateurRepository;
        this.zoneRepository = zoneRepository;
        this.commissionLocaleRepository = commissionLocaleRepository;
        this.circonscriptionRepository = circonscriptionRepository;
		this.bureauVoteRepository = bureauVoteRepository;
    }

    /**
     * Génère le Token JWT de l'utilisateur.
     *
     * @param utilisateur l'utilisateur.
     * @return le Token JWT de l'utilisateur.
     */
    private String genererTokenJwt(Utilisateur utilisateur) {
        return jwtTokenUtils.generateToken(utilisateur);
    }

    /**
     * Récupère un utilisateur à partir de son username.
     *
     * @param username le username de l'utilisateur.
     * @return L'utilisateur.
     * @throws UsernameNotFoundException Exception levé lorsqu'aucun utilisateur ne correspond à ce username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByUsername(username);
        if (utilisateurOptional.isPresent()) {
            return utilisateurOptional.get().buildUser();
        } else {
            throw new UsernameNotFoundException("Aucun utilisateur trouvé pour le username: " + username);
        }
    }

    /**
     * Récupère un utilisateur à partir de son username et de son password.
     *
     * @param username le username de l'utilisateur.
     * @param password le password de l'utilisateur.
     * @return L'utilisateur.
     * @throws UsernameNotFoundException Exception levé lorsqu'aucun utilisateur ne correspond à ce username.
     */
    private Utilisateur rechercherUtilisateurParUsernameEtPassword(String username, String password) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByUsername(username);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            if (SecurityService.comparerPassword(password, utilisateur.getPassword())) {
                return utilisateur;
            }
        }

        throw new UsernameNotFoundException("Aucun utilisateur trouvé pour le username: " + username);
    }

    /**
     * Authentifie l'utilisateur.
     *
     * @param authDto l'utilisateur.
     * @return le Tokent JWT de l'utilisateur authentifié.
     */
    public TokenDto autentifierUtilisateur(AuthDto authDto) {
        Utilisateur utilisateur = rechercherUtilisateurParUsernameEtPassword(authDto.getUsername(), authDto.getPassword());
        SecurityContextHolder.clearContext();
        String token = genererTokenJwt(utilisateur);

        return new TokenDto(StringUtils.join(of("Bearer", token), " "));
    }

    /**
     * Compare les mots de passe.
     *
     * @param password       le mot de passe.
     * @param encodePassword le mot de passe crypté.
     * @return le mot de passe crypté.
     */
    private static boolean comparerPassword(String password, String encodePassword) {
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        return bCryptPasswordEncoder.matches(password, encodePassword);
    }

    /**
     * Crypte le mot de passe de l'utilisateur.
     *
     * @param password le mot de passe.
     * @return le mot de passe crypté.
     */
    public static String crypterPassword(String password) {
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        return bCryptPasswordEncoder.encode(password);
    }

    @Transactional
    public Set<String> recupererCodeRegionsAutorisesPourUtilisateurConnecte() {
        String login = SecurityUtils.getLoginUtilisateur();
        Utilisateur utilisateur = utilisateurRepository.findByUsername(login).orElse(null);

        if (utilisateur != null) {
            if (utilisateur.getRole() == Role.DIRECTION_CENTRALE || utilisateur.getRole() == Role.ADMIN) {
                // Si l'utilisateur est de la direction centrale, il a le droit sur toutes les regions
                return zoneRepository.listerEnList(REGION).stream()
                        .map(Zone::getCode)
                        .collect(toSet());
            } else if (utilisateur.getRole() == Role.SUPERVISEUR_REGIONAL) {
                return zoneRepository.listerEnList(REGION).stream()
                        .filter(region -> region.getLoginSuperviseur() != null && region.getLoginSuperviseur().equals(login))
                        .map(Zone::getCode)
                        .collect(toSet());
            } else if (utilisateur.getRole() == Role.RESPONSABLE_CEL) {

                return bureauVoteRepository.lister().stream()
                        .filter(bureauVote -> bureauVote.loginResponsable() != null && bureauVote.loginResponsable().equals(login))
                        .map(BureauVote::codeRegion)
                        .collect(toSet());
            }
        }
        return emptySet();
    }

    @Transactional
    public List<String> recupererCodeCommissionLocalesAutorisesPourUtilisateurConnecte() {
        String login = SecurityUtils.getLoginUtilisateur();
        Utilisateur utilisateur = utilisateurRepository.findByUsername(login).orElse(null);

        if (utilisateur != null) {
            if (utilisateur.getRole() == Role.DIRECTION_CENTRALE || utilisateur.getRole() == Role.ADMIN) {
                // Si l'utilisateur est de la direction centrale, il a le droit sur toutes les regions
                return commissionLocaleRepository.listerEnList().stream()
                        .map(CommissionLocale::getCode)
                        .collect(toList());
            } else if (utilisateur.getRole() == Role.SUPERVISEUR_REGIONAL) {
                List<String> regions = zoneRepository.listerEnList(REGION).stream()
                        .filter(region -> region.getLoginSuperviseur() != null && region.getLoginSuperviseur().equals(login))
                        .map(Zone::getCode)
                        .collect(toList());
                return bureauVoteRepository.lister().stream()
                        .filter(bureauVote ->regions.contains(bureauVote.codeRegion()))
                        .map(BureauVote::codeCommissionLocale)
                        .collect(toList());
            } else if (utilisateur.getRole() == Role.RESPONSABLE_CEL) {

                return commissionLocaleRepository.listerEnList().stream()
                        .filter(commissionLocale1 -> commissionLocale1.getLoginResponsable() != null && commissionLocale1.getLoginResponsable().equals(login))
                        .map(CommissionLocale::getCode)
                        .collect(toList());
            }
        }
        return emptyList();
    }

    @Transactional
    public List<String> recupererCodeCirconscriptionAutorisesPourUtilisateurConnecte() {
        String login = SecurityUtils.getLoginUtilisateur();
        Utilisateur utilisateur = utilisateurRepository.findByUsername(login).orElse(null);

        if (utilisateur != null) {
            if (utilisateur.getRole() == Role.DIRECTION_CENTRALE || utilisateur.getRole() == Role.ADMIN) {
                // Si l'utilisateur est de la direction centrale, il a le droit sur toutes les regions
                return circonscriptionRepository.listerEnList().stream()
                        .map(Circonscription::getCode)
                        .collect(toList());
            } else if (utilisateur.getRole() == Role.SUPERVISEUR_REGIONAL) {
                List<String> regions = zoneRepository.listerEnList(REGION).stream()
                        .filter(region -> region.getLoginSuperviseur() != null && region.getLoginSuperviseur().equals(login))
                        .map(Zone::getCode)
                        .collect(toList());
				return bureauVoteRepository.lister().stream()
						.filter(bureauVote ->regions.contains(bureauVote.codeRegion()))
						.map(BureauVote::codeCirconscription)
						.collect(toList());
            } else if (utilisateur.getRole() == Role.RESPONSABLE_CEL) {

                return commissionLocaleRepository.listerEnList().stream()
                        .filter(commissionLocale1 -> commissionLocale1.getLoginResponsable() != null && commissionLocale1.getLoginResponsable().equals(login))
                        .map(CommissionLocale::codeCirconscription)
                        .collect(toList());
            }
        }
        return emptyList();
    }
}
