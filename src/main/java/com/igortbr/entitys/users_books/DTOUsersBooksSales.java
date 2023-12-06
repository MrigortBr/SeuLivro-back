package com.igortbr.entitys.users_books;

import java.util.Date;
import java.util.UUID;

public record DTOUsersBooksSales(String image,Date datePurchase, double price, int quantity, String title, UUID idBook) {
	public DTOUsersBooksSales(UsersBook usersbook) {
		this(
				usersbook.getIdBook().getImage(),
				usersbook.getDatePurchase(),
				usersbook.getPrice(),
				usersbook.getQuantity(),
				usersbook.getIdBook().getTitle(),
				usersbook.getIdBook().getId()

			);
	}
}
