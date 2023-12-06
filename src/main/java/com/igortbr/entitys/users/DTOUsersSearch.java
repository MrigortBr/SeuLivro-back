package com.igortbr.entitys.users;

import java.util.UUID;

public record DTOUsersSearch(UUID id, String image, String nameAuthor, String nickname) {
	public DTOUsersSearch(Users user) {
		this(user.getId(), user.getImage(), user.getFirstName() +" "+ user.getLastName(), user.getNickname());
	}
	
}
