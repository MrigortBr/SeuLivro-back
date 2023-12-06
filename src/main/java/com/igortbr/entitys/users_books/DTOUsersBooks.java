package com.igortbr.entitys.users_books;

import java.util.Date;
import java.util.UUID;

public record DTOUsersBooks(String image,Date datePurchase, double price, int quantity, String title, String nameStore, UUID idStore, UUID idBook, boolean visible) {
	public DTOUsersBooks(UsersBook usersbook) {
		this(
				usersbook.getIdBook().getImage(),
				usersbook.getDatePurchase(),
				usersbook.getPrice(),
				usersbook.getQuantity(),
				usersbook.getIdBook().getTitle(),
				usersbook.getIdBook().getStore() != null ? usersbook.getIdBook().getStore().getName() : "Loja Desconhecida",
				usersbook.getIdBook().getStore() != null ? usersbook.getIdBook().getStore().getId() : null,
				usersbook.getIdBook().getId(),
				usersbook.getIdBook().isEnabled()

			);
	}
}
