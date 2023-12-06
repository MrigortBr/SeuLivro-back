package com.igortbr.entitys.books;

public record DTOBooksRegister(
		String title, 
		int publicationYear,
		String genre,
		String description,
		double price,
		int stockQuantity,
		boolean enabled,
		String image) {

}
