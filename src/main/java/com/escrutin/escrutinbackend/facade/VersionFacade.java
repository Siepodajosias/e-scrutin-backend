package com.escrutin.escrutinbackend.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.escrutin.escrutinbackend.controller.dto.VersionDto;

import static com.escrutin.escrutinbackend.exception.VersionException.erreurDeRecuperationDeVersion;

@Service
public class VersionFacade {

	/**
	 * Retourne la version et le sha1 de l'application.
	 *
	 * @return la version et le sha1 de l'application.
	 */
	public VersionDto recupererVersionEtSha1() {
		try {
			File file = ResourceUtils.getFile("classpath:app.properties");
			InputStream input = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(input);
			String version = properties.getProperty("app.version", "");
			String sha1 = properties.getProperty("app.git.sha1", "");
			return new VersionDto(version, sha1);
		}
		catch (IOException ex) {
			throw erreurDeRecuperationDeVersion();
		}
	}
}
