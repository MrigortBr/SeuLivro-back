package com.igortbr.infra.exceptions;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class UsersResponses {

	public static ResponseEntity createOkRequest(String message) throws IOException {
		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.OK, message,
				Instant.now().toString());
		;
		return ResponseEntity.status(DTOresponse.getCode()).body(DTOresponse);
	}
	
	public static ResponseEntity createNotFoundRequest(String message) throws IOException {
		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.NOT_FOUND, message,
				Instant.now().toString());
		;
		return ResponseEntity.status(DTOresponse.getCode()).body(DTOresponse);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {

		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(),
				Instant.now().toString());
		;
		return ResponseEntity.status(DTOresponse.getCode()).body(DTOresponse);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity handleBadCredentialsException(InternalAuthenticationServiceException ex, WebRequest request) {
		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.NOT_FOUND, "Senha ou Email incorretos, tente novamente",
				Instant.now().toString());
		;
		return ResponseEntity.badRequest().body(DTOresponse);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity dataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String messageError = ex.getMessage();
		if (messageError.contains("users_nickname_key")) {
			messageError = "Apelido ja está em uso";
		} else if (messageError.contains("users_email_key")) {
			messageError = "O Email informado já está registrado";
		}else {
			if (messageError.contains("null value in column \"firstName\"")) {
				messageError = "O campo 'Primeiro Nome' não pode ser vazio ";
			} else if (messageError.contains("null value in column \"lastName\"")) {
				messageError = "O campo 'Ultimo Nome' não pode ser vazio";
			} else if (messageError.contains("null value in column \"nickname\"")) {
				messageError = "O campo 'Apelido' não pode ser vazio";
			} else if (messageError.contains("null value in column \"birthDate\"")) {
				messageError = "O campo 'Aniversario' não pode ser vazio";
			} else if (messageError.contains("null value in column \"password\"")) {
				messageError = "O campo 'Senha' não pode ser vazio";
			} else if (messageError.contains("null value in column \"email\"")) {
				messageError = "O campo 'Email' não pode ser vazio";
			} else {
				messageError = "Parâmetros inválidos";
			}
		}

		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.BAD_REQUEST, messageError, Instant.now().toString());
		return ResponseEntity.status(DTOresponse.getCode()).body(DTOresponse);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request, HttpServletResponse response) throws IOException {
		
		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.BAD_REQUEST,
				"Argumentos passados incorretos.", "Not Finded");
		;
		generateResponse(DTOresponse, response);

	}

	@ExceptionHandler(TokenExpiredException.class)
	public static void handleTokenExpiredException(TokenExpiredException e, HttpServletResponse response)
			throws IOException {
		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.UNPROCESSABLE_ENTITY,
				"O token de autenticação expirou.", e.getExpiredOn().toString());
		;
		generateResponse(DTOresponse, response);
	}

	@ExceptionHandler(JWTDecodeException.class)
	public static void jwtDecodeException(JWTDecodeException e, HttpServletResponse response) throws IOException {
		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.UNAUTHORIZED, "O Token de autenticação é invalido, Encaminahndo para pagina de Login",
				Instant.now().toString());
		;
		generateResponse(DTOresponse, response);
	}

	private static void generateResponse(DTOExceptions DTOresponse, HttpServletResponse response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(DTOresponse);
		response.setStatus(DTOresponse.getCode().value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonResponse);
	}
}
