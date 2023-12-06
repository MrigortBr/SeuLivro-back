package com.igortbr.infra.authentication;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.igortbr.entitys.users.Users;


@Service
public class TokenService {
	@Value("asdlasdaksdkasdkasdkask")
	private String secret;
	
	@Value("seu_livro")
	private String issuer;
	
	public String generateToken(Users user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer(issuer)
					.withSubject(user.getEmail())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException ex) {
			throw new RuntimeException("Error while generating token",ex);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String isTokenTrue = JWT.require(algorithm)
					.withIssuer(issuer)
					.build()
					.verify(token)
					.getSubject();
			return isTokenTrue;
		} catch (JWTCreationException ex) {
			throw new RuntimeException("Error while generating token");
		}
	}
	
	
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
