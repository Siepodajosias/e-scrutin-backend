package com.escrutin.escrutinbackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Value("${client.url.local}")
	private String CLIENT_LOCAL;

	@Value("${client.url.dev}")
	private String CLIENT_DEV;

	@Value("${client.url.prod}")
	private String CLIENT_PROD;

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
				.allowedOrigins(CLIENT_LOCAL, CLIENT_DEV, CLIENT_PROD)
				.allowedMethods("*")
				.maxAge(3600L)
				.allowedHeaders("*")
				.exposedHeaders("Authorization")
				.allowCredentials(true);
	}
}
