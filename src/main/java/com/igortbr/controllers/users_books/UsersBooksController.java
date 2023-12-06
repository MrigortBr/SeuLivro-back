package com.igortbr.controllers.users_books;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.Users;
import com.igortbr.entitys.users_books.DTOUsersBooks;
import com.igortbr.entitys.users_books.DTOUsersBooksSales;
import com.igortbr.entitys.users_books.UsersBook;
import com.igortbr.infra.authentication.TokenService;
import com.igortbr.model.users_books.RepositoryUsersBooks;
import com.igortbr.services.auth.AuthorizationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/history")
@CrossOrigin
public class UsersBooksController {

	@Autowired
	private RepositoryUsersBooks repository;
	
	@Autowired
	private AuthorizationService service;
	
	@GetMapping
	public ResponseEntity getHistoryByUser(HttpServletRequest request) {
		Users user = service.getUserByRequest(request);
		List<DTOUsersBooks> response = repository.findByidUser(user).stream().map(DTOUsersBooks::new).toList();
		
		return ResponseEntity.ok().body(response);
		
		
	}
	
	@GetMapping("/store")
	public ResponseEntity getHistoryByStore (HttpServletRequest request) {
		Stores store = service.getUserByRequest(request).getStore();
		List<Books> books = store.getBooks();
		
		List<UsersBook> usersBook = new ArrayList<UsersBook>();
		
		books.forEach(book ->{
			usersBook.addAll(repository.findByidBook(book));
		});
		
		List<DTOUsersBooksSales> response = usersBook.stream().map(DTOUsersBooksSales::new).toList();

		
		return ResponseEntity.ok().body(response);
	}
}
