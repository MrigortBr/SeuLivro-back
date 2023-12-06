package com.igortbr.controllers.stores;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.stores.DTOStoreCreate;
import com.igortbr.entitys.stores.DTOStoreRead;
import com.igortbr.entitys.stores.DTOStoreUpdate;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.Users;
import com.igortbr.infra.exceptions.UsersResponses;
import com.igortbr.model.books.RepositoryBooks;
import com.igortbr.model.stores.RepositoryStores;
import com.igortbr.services.stores.StoresService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/store")
@CrossOrigin

public class StoreController {

	@Autowired
	RepositoryStores repository;
	
	@Autowired
	RepositoryBooks repositoryBooks;

	@Autowired
	private StoresService service;

	@PostMapping
	@Transactional
	public ResponseEntity create(@RequestBody DTOStoreCreate newStore, HttpServletRequest request, HttpServletResponse response) {
		Users owner = service.token.getUserByRequest(request);
		Boolean haveStore = service.userHaveStore(owner);
		if (haveStore) {
			return ResponseEntity.ok("VC JA TEM LOJA");
		} else {
			Stores store = new Stores(newStore, owner);
			try {
				repository.save(store);
				DTOStoreRead storeResponse = new DTOStoreRead(store);
				return ResponseEntity.ok().body(storeResponse);
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}

	@GetMapping
	public ResponseEntity readMyStore(HttpServletRequest request) {
		Users owner = service.token.getUserByRequest(request);
		Boolean haveStore = service.userHaveStore(owner);
		if (haveStore == false) {
			return ResponseEntity.notFound().build();
		} else {
			DTOStoreRead storeResponse = new DTOStoreRead(owner.getStore());
			return ResponseEntity.ok().body(storeResponse);
		}
	}
	
	@GetMapping("/{uuidStore}")
	public ResponseEntity readStore(@PathVariable UUID uuidStore) {

		Optional<Stores> store = repository.findById(uuidStore);
		if (store.get() != null) {
			DTOStoreRead storeResponse = new DTOStoreRead(store.get());
			return ResponseEntity.ok().body(storeResponse);
		}else {
			return ResponseEntity.ok().body("Loja não Encontrada");
		}
		
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity update(@RequestBody DTOStoreUpdate storeChanged, HttpServletRequest request) throws IOException {
		Users owner = service.token.getUserByRequest(request);
		Stores store = owner.getStore();
		store = service.update(store, storeChanged);
		return UsersResponses.createOkRequest("Loja Atualizada com sucesso")	;
		}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity delete(HttpServletRequest request) throws IOException {
		Users owner = service.token.getUserByRequest(request);
		List<Books> books = owner.getStore().getBooks();
		for (Books book: books) {
			if (book != null) {
				repositoryBooks.delete(book);
			}
		}
		repository.delete(owner.getStore());
		return UsersResponses.createOkRequest("Loja deletada com sucesso");
	}
	
	@DeleteMapping("/visible")
	@Transactional
	public ResponseEntity hiddenAndVisibleStore(HttpServletRequest request) {
		Users owner = service.token.getUserByRequest(request);
		Stores store = owner.getStore();
		return ResponseEntity.ok().body(service.enableOrDisableStore(store));
	}
	
	

}
