package com.escrutin.escrutinbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/photos/**")
				.addResourceLocations(
						System.getProperty("os.name").startsWith("Windows") ?
								"file:///C:/projets/recensement-backend/photos/" :
								"file:/home/alpha/public_html/war/recensement/photos/"
				);
	}
}