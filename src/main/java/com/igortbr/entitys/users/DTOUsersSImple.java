package com.igortbr.entitys.users;

import java.util.UUID;

public record DTOUsersSImple(UUID id, String nickname, String image) {
	public DTOUsersSImple(Users user) {
		this(user.getId(), user.getNickname(), user.getImage());
	}
}
