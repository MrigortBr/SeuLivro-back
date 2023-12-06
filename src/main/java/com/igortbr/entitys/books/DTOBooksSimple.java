package com.igortbr.entitys.books;

import java.util.UUID;

public record DTOBooksSimple(String title, String image, UUID id) {
	public DTOBooksSimple(Books book) {
		this(book.getTitle(), book.getImage(), book.getId());
	}
}
