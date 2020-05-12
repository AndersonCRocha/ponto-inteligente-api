package com.anderson.pontointeligente.api.security.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.pontointeligente.api.security.dtos.JwtAuthenticationDTO;
import com.anderson.pontointeligente.api.security.dtos.TokenDTO;
import com.anderson.pontointeligente.api.security.util.JwtTokenUtil;
import com.anderson.pontointeligente.api.utils.Response;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	public static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping
	public ResponseEntity<Response<TokenDTO>> gerarTokenJwt(
			@Valid @RequestBody JwtAuthenticationDTO authenticationDTO,
			BindingResult result) throws AuthenticationException{
		
		Response<TokenDTO> response = new Response<>();
		
		if(result.hasErrors()) {
			log.error("Erro validando dados de autenticação: {}"+ authenticationDTO);
			result.getAllErrors()
				.forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		log.info("Gerando token para o email : {}", authenticationDTO.getEmail());
		Authentication authentication = 
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());
		String token = jwtTokenUtil.obterToken(userDetails);
		response.setData(new TokenDTO(token));
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<Response<TokenDTO>> gerarRefreshTokenJwt(HttpServletRequest request){
		
		log.info("Gerando refresh do token JWT");
		Response<TokenDTO> response = new Response<TokenDTO>();
		
		Optional<String> tokenOpt = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
		
		if(tokenOpt.isPresent() && tokenOpt.get().startsWith(BEARER_PREFIX)) {
			tokenOpt = Optional.of(tokenOpt.get().substring(7));
		}
		
		if(!tokenOpt.isPresent()) {
			response.getErrors().add("Token não informado");
		} else if(!jwtTokenUtil.tokenValido(tokenOpt.get())) {
			response.getErrors().add("Token inválido ou expirado");
		}
		
		if(!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}
		
		String refreshedToken = jwtTokenUtil.refreshToken(tokenOpt.get());
		response.setData(new TokenDTO(refreshedToken));
		
		return ResponseEntity.ok(response);
	}
	
}
