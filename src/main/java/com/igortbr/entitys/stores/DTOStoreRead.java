package com.igortbr.entitys.stores;

import java.util.List;
import java.util.UUID;

import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.users.Users;

public record DTOStoreRead(String cnpj, String name, String fantasyName,
		String description, boolean enabled, String image, List<Books> books) {
	public DTOStoreRead(Stores store) {
		this(
				store.getCnpj(), store.getName(), 
				store.getFantasyName(), 
				store.getDescription(),
				store.isEnabled(),
				store.getImage(),
				store.getBooks()
			);	
	}
}
