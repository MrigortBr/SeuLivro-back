package com.igortbr.model.users_books;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.users.Users;
import com.igortbr.entitys.users_books.UsersBook;

public interface RepositoryUsersBooks extends JpaRepository<UsersBook, Long>{
	List<UsersBook> findByidUser(Users user);
	List<UsersBook> findByidBook(Books books);
}
