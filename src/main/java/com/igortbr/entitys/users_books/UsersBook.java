package com.igortbr.entitys.users_books;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.igortbr.entitys.address.Address;
import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.users.Users;
import com.igortbr.model.books.RepositoryBooks;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsersBook {	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id", nullable = false)
    private Users idUser;

    @ManyToOne
    @JoinColumn(name = "idBook", referencedColumnName = "id", nullable = false)
    private Books idBook;
    
    @ManyToOne
    @JoinColumn(name = "idAddress", referencedColumnName = "id", nullable = false)
    private Address idAddress;

    private double price;
    private int quantity;

    @PastOrPresent
    private Date datePurchase;
    
    public UsersBook(Users user, Books book, int quantity, Address address) {
        Calendar calendar = Calendar.getInstance();
        this.datePurchase = (@PastOrPresent Date) calendar.getTime();
        this.price = book.getPrice() * quantity;
        this.idBook = book;
        this.idUser = user;
        this.quantity = quantity;
        this.idAddress = address;
    }

}
