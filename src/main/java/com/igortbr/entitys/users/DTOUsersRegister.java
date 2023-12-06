package com.igortbr.entitys.users;

import java.util.Date;
import java.util.Optional;

import com.igortbr.entitys.users.identifier.Identifier;
import com.igortbr.entitys.users.roles.RolesApp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.NoArgsConstructor;
public record DTOUsersRegister(String firstName,
		String lastName,
		String nickname,
		@Past Date birthDate,
		@Email String email,
		String password,
		String identifier,
		Identifier typeIdentifier,
		Optional<RolesApp> role,
		String image) {

	public DTOUsersRegister setRole(RolesApp role) {
		return new DTOUsersRegister(firstName, lastName, nickname, birthDate, email, password,identifier, typeIdentifier, Optional.ofNullable(role), image);
	}
	
}
