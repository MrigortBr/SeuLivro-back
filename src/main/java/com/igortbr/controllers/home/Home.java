package com.igortbr.controllers.home;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igortbr.entitys.books.DTOBooksSimple;
import com.igortbr.entitys.home.DTOHomeReturn;
import com.igortbr.entitys.stores.DTOStoreSimple;
import com.igortbr.entitys.users.DTOUsersSImple;
import com.igortbr.infra.authentication.TokenService;
import com.igortbr.model.books.RepositoryBooks;
import com.igortbr.model.stores.RepositoryStores;
import com.igortbr.model.users.Repository;
import com.igortbr.services.auth.AuthorizationService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/home")
@Tag(name = "Pagina Principal", description = "Listagem Rota principal")
public class Home {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private Repository repository;
	
	@Autowired
	private RepositoryBooks repositoryBooks;
	
	@Autowired
	private RepositoryStores repositoryStores;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthorizationService service;
	
	
	@GetMapping
	@CrossOrigin
	public ResponseEntity home() {
		List<DTOBooksSimple> books = repositoryBooks.findRandomBooks().stream().map(DTOBooksSimple::new).toList();
		List<DTOUsersSImple> authors = repository.findRandomUsers().stream().map(DTOUsersSImple::new).toList();
		List<DTOStoreSimple> stores = repositoryStores.findRandomStores().stream().map(DTOStoreSimple::new).toList();
		DTOHomeReturn response = new DTOHomeReturn(books, authors, stores);
		return ResponseEntity.ok(response);
		
	}
	
	
	


}
