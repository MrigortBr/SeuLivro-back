package com.igortbr.entitys.users;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.igortbr.entitys.books.DTOBooksSimple;
import com.igortbr.entitys.users.identifier.Identifier;
import com.igortbr.entitys.users.roles.RolesApp;

public record DTOUsersRead(UUID id, String firstName, String lastName, Date birthDate, String email,
		 boolean enabled, String nickname, String identifier, Identifier typeIdentifier, String image) {
	
	public DTOUsersRead(Users user) {
		this(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getBirthDate(),
				user.getEmail(),
				user.isEnabled(),
				user.getNickname(),
				user.getIdentifier(),
				user.getTypeIdentifier(),
				user.getImage()
			);
	}
}
