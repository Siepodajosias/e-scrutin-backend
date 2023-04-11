package com.escrutin.escrutinbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static java.util.List.of;
import static org.apache.commons.lang3.StringUtils.join;

@Service
public class PhotoService {
	Logger logger = LoggerFactory.getLogger(PhotoService.class);
	@Value("${photo.windows_base_path}")
	public String WINDOWS_BASE_PATH;

	@Value("${photo.linux_base_path}")
	public String LINUX_BASE_PATH;


	/**
	 * PErmet de télécharger un fichier sur le serveur.
	 *
	 * @param photo la photo.
	 * @param dossier le dossier de stockage.
	 *
	 * @return le nom du fichier.
	 * @throws IOException levée en cas d'échec du téléchargement.
	 */
	public String uploadPhoto(MultipartFile photo, String dossier) throws IOException {
		logger.info("************* Téléchargement de la photo vers /" + dossier + " en cours...");
		String baseUrl = System.getProperty("os.name").startsWith("Windows") ? WINDOWS_BASE_PATH : LINUX_BASE_PATH;
		String nomPhoto = photo.getOriginalFilename();
		new File(join(of(baseUrl, dossier), "/")).mkdirs();
		String path = join(of(baseUrl, dossier, nomPhoto), "/");
		photo.transferTo(new File(path));
		logger.info("************* Téléchargement de la photo vers /" + dossier + " terminée avec succès");

		return nomPhoto;
	}
}
