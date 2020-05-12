package com.anderson.pontointeligente.api.security.dtos;

public class TokenDTO {

	private String token;

	public TokenDTO(){
	}
	
	public TokenDTO(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
