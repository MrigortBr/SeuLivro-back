package com.igortbr.entitys.books;

import java.util.UUID;

public record DTOBookUpdate(UUID id, String title, int publicationYear, String genre, String description, double price,
		int stockQuantity, boolean enabled, UUID storeUUID,String storeName, String author, UUID authorId, String image) {

}
