package com.escrutin.escrutinbackend.auth;

import com.escrutin.escrutinbackend.controller.dto.AuthDto;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthDtoMockBuilder {
	private String username;
	private String password;

	public AuthDtoMockBuilder() {
	}

	public AuthDto build() {
		AuthDto authDto = mock(AuthDto.class);

		when(authDto.getUsername()).thenReturn(username);
		when(authDto.getPassword()).thenReturn(password);
		return authDto;
	}

	public AuthDtoMockBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	public AuthDtoMockBuilder setPassword(String password) {
		this.password = password;
		return this;
	}
}
