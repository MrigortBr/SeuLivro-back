package com.igortbr.entitys.books;

import java.util.UUID;

public record DTOBooksSearch(UUID id, String title,Double price ,String nameStore, String nameAuthor, String image) {

	public DTOBooksSearch(Books book) {
		this(
				book.getId(),
				book.getTitle(),
				book.getPrice(),
				book.getStore() != null ? book.getStore().getName() : "Autor Desconhecido",
				book.getAuthor() != null ? book.getAuthor().getNickname() : "Autor Desconhecido",
				book.getImage()	
			);
	}
}
