package com.igortbr.entitys.books;

import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "book")
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data

public class Books {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String title;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "author", referencedColumnName = "id")
	private Users author;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "store", referencedColumnName = "id")
	private Stores store;

	private int publicationYear;
	private String genre;
	private String description;
	private double price;
	private int stockQuantity;
	private boolean enabled;
	private String image;

	public Books(DTOBooksRegister newBook) {
		this.title = newBook.title();
		this.publicationYear = newBook.publicationYear();
		this.genre = newBook.genre();
		this.description = newBook.description();
		this.price = newBook.price();
		this.stockQuantity = newBook.stockQuantity();
		this.enabled = newBook.enabled();
		this.image = newBook.image();

	}

	public void update(DTOBookUpdate newBook) {
		if (newBook.title() != null && !newBook.title().equals(this.title)) {
			this.title = newBook.title();
		}

		if (newBook.publicationYear() != 0 && !(newBook.publicationYear() == this.publicationYear)) {
			this.publicationYear = newBook.publicationYear();
		}

		if (newBook.genre() != null && !newBook.genre().equals(this.genre)) {
			this.genre = newBook.genre();
		}

		if (newBook.price() != 0  && !(newBook.price() == this.price)) {
			this.price = newBook.price();
		}

		if (newBook.enabled() != this.enabled) {
			this.enabled = newBook.enabled();
		}

		if (newBook.image() != null && !newBook.image().equals(this.image)) {
			this.image = newBook.image();
		}
		
		if (newBook.stockQuantity() != 0 && !(newBook.stockQuantity() == this.stockQuantity)) {
			this.stockQuantity = newBook.stockQuantity();
		}
		
		

	}
}
