package com.igortbr.entitys.books;

import java.util.UUID;

public record DTOBooksRead(UUID id, String title, int publicationYear, String genre, String description, double price,
		int stockQuantity, boolean enabled, UUID storeUUID,String storeName, String author, UUID authorId, String image) {

	public DTOBooksRead(Books book) {
		this(
				book.getId(),
				book.getTitle(),
				book.getPublicationYear(),
				book.getGenre(),
				book.getDescription(),
				book.getPrice(),
				book.getStockQuantity(),
				book.isEnabled(),
				book.getStore().getId(),
				book.getStore().getFantasyName(),
				book.getAuthor() != null ? book.getAuthor().getNickname() : "Autor Desconhecido",
				book.getAuthor() != null ? book.getAuthor().getId() : null,
				book.getImage()
				
			);
	}
}
