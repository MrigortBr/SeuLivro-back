package com.igortbr.controllers.books;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igortbr.entitys.address.Address;
import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.books.DTOBookUpdate;
import com.igortbr.entitys.books.DTOBooksBuy;
import com.igortbr.entitys.books.DTOBooksRead;
import com.igortbr.entitys.books.DTOBooksRegister;
import com.igortbr.entitys.books.DTOBooksSearch;
import com.igortbr.entitys.books.DTOBooksSimple;
import com.igortbr.entitys.invite.Invites;
import com.igortbr.entitys.invite.status.Status;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.Users;
import com.igortbr.entitys.users_books.UsersBook;
import com.igortbr.infra.exceptions.UsersResponses;
import com.igortbr.model.address.RepositoryAddress;
import com.igortbr.model.books.RepositoryBooks;
import com.igortbr.model.invite.RepositoryInvite;
import com.igortbr.model.users_books.RepositoryUsersBooks;
import com.igortbr.services.auth.AuthorizationService;

import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/book")
@Api(value="API REST Produtos")
@CrossOrigin
public class BooksController {

	@Autowired
	RepositoryBooks repository;

	@Autowired
	RepositoryUsersBooks repositoryPuchase;
	
	@Autowired
	RepositoryAddress repositoryAddress;
	
	@Autowired
	RepositoryInvite repositoryInvite;

	@Autowired
	private AuthorizationService service;

	@GetMapping
	public ResponseEntity getMyBooks(HttpServletRequest request) {
		Stores store = service.getUserByRequest(request).getStore();
		List<DTOBooksRead> myBooks = store.getBooks().stream().map(DTOBooksRead::new).toList();
		
		return ResponseEntity.ok().body(myBooks);
	}
	
	@GetMapping("/search")
	public ResponseEntity getBookByName(@RequestParam String title) {
		List<Books> books =  repository.findByTitleContainingAllIgnoreCaseAndEnabledTrue(title);
		
		List<DTOBooksSearch> myBooks = books.stream().map(DTOBooksSearch::new).toList();
		
		return ResponseEntity.ok().body(myBooks);
		
	}
	
	@GetMapping("/search/genre")
	public ResponseEntity getBookByGenre(@RequestParam String title) {
		List<Books> books =  repository.findByGenreContainingAllIgnoreCaseAndEnabledTrue(title);
		
		List<DTOBooksSearch> myBooks = books.stream().map(DTOBooksSearch::new).toList();
		
		return ResponseEntity.ok().body(myBooks);
		
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity updateBook(@RequestBody DTOBookUpdate updated, HttpServletRequest request) throws IOException {
		Optional<Books> optionalBook = repository.findById(updated.id());
		
		if(optionalBook.get() != null) {
			Books book = optionalBook.get();
			book.update(updated);
			return UsersResponses.createOkRequest("Livro Atualizado com sucesso");
		}else {
			return UsersResponses.createNotFoundRequest("Livro não encontrado");
		}
		
		
	}

	@PostMapping("/register")
	@Transactional
	public ResponseEntity register(@RequestBody DTOBooksRegister newBook, HttpServletRequest request) {
		Stores store = service.getUserByRequest(request).getStore();
		
		Books book = new Books(newBook);
		book.setStore(store);
		repository.save(book);

		return ResponseEntity.ok().body(new DTOBooksRead(book));
	}

	@GetMapping("/{uuidBook}")
	public ResponseEntity listByUuid(@PathVariable UUID uuidBook) {
		Optional<Books> bookOptional = repository.findById(uuidBook);

		if (bookOptional.isPresent()) {
			if (bookOptional.get().isEnabled()) {
				return ResponseEntity.ok().body(new DTOBooksRead(bookOptional.get()));
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/buy")
	@Transactional
	public ResponseEntity buyBookByUUID(@RequestBody DTOBooksBuy buy,
			HttpServletRequest request) throws IOException {
		Optional<Books> bookOptional = repository.findById(buy.idBook());
		Optional<Address> addressOptional = repositoryAddress.findById(buy.idAddress());
		Users user = service.getUserByToken(service.getToken(request));
		if (bookOptional.isPresent() && addressOptional.isPresent()) {
			if (bookOptional.get().isEnabled()) {
				Books book = bookOptional.get();
				if (book.getStockQuantity() >= buy.quantity()) {
					UsersBook userBook = null;
					if (buy.quantity() != null) userBook = new UsersBook(user, book, buy.quantity(), addressOptional.get());
					else userBook = new UsersBook(user, book, 1, addressOptional.get());
					book.setStockQuantity(book.getStockQuantity() - buy.quantity());
					repositoryPuchase.save(userBook);
					return UsersResponses.createOkRequest("Livro Comprado Com sucesso");
				}else {
					return UsersResponses.createNotFoundRequest("Sem estoque para essa quantidade, estoque disponivel:"+book.getStockQuantity());
				}
			} else {
				return UsersResponses.createNotFoundRequest("Livro não foi encontrado, tente novamente mais tarde");
			}
		} else {
			return UsersResponses.createNotFoundRequest("Livro ou Endereço não foi encontrado, tente novamente mais tarde");
		}
	}
	
	@GetMapping("/invite/{uuidBook}")
	@Transactional
	public ResponseEntity inviteAuthor(@PathVariable UUID uuidBook, HttpServletRequest request) {
		Optional<Books> book = repository.findById(uuidBook);
		List<Invites> invites =  repositoryInvite.findByBookIdAndStatusNot(uuidBook, Status.FINISHED);
		if (book.get() != null) {
			if (invites.size() == 0) {
				Invites invite = new Invites(book.get());
				invite = repositoryInvite.save(invite);
				return ResponseEntity.ok().body(invite.createLink());
			}else {
				
				return ResponseEntity.ok().body(invites.get(0).createLink());
			}

		}else {
			return ResponseEntity.ok().build();
		}
	}
	
	@GetMapping("/invite/open/{codeInvite}")
	public ResponseEntity readInvite(@PathVariable byte[] codeInvite, HttpServletRequest request) {
		Invites invite = new Invites();
		UUID uuid = UUID.fromString(invite.loadLink(codeInvite));
		Optional<Invites> inviteFinded = repositoryInvite.findById(uuid);
		if (inviteFinded.get() != null) {
			return ResponseEntity.ok().body(new DTOBooksSimple(inviteFinded.get().getBook()));
		}else {
			return ResponseEntity.ok().build();
		}
	}
	
	@GetMapping("/invite/accept/{codeInvite}")
	@Transactional
	public ResponseEntity acceptInvite(@PathVariable byte[] codeInvite, HttpServletRequest request) {
		Invites invite = new Invites();
		UUID uuid = UUID.fromString(invite.loadLink(codeInvite));
		Optional<Invites> inviteFinded = repositoryInvite.findById(uuid);
		Users user = service.getUserByRequest(request);
		if (inviteFinded.get() != null) {
			Books book = inviteFinded.get().getBook();
			book.setAuthor(user);
			return ResponseEntity.ok().body("Voce agora é autor do livro");
		}else {
			return ResponseEntity.ok().build();
		}
	}
}
