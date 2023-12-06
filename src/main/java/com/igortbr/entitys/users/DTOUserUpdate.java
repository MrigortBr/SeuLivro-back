package com.igortbr.entitys.users;

import java.util.Date;
import java.util.Optional;

import com.igortbr.entitys.users.identifier.Identifier;
import com.igortbr.entitys.users.roles.RolesApp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

public record DTOUserUpdate(
		String firstName, 
		  String lastName,
		  String nickname,
		  Date birthDate,
		  String email,
		  String password,
		  String identifier,
		  Identifier typeIdentifier,
		  String image) {

}
