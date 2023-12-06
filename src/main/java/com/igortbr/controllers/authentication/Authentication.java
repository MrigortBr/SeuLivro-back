package com.igortbr.controllers.authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.books.DTOBooksSearch;
import com.igortbr.entitys.books.DTOBooksSimple;
import com.igortbr.entitys.users.DTOUserUpdate;
import com.igortbr.entitys.users.DTOUsersLogin;
import com.igortbr.entitys.users.DTOUsersRead;
import com.igortbr.entitys.users.DTOUsersReadProfile;
import com.igortbr.entitys.users.DTOUsersRegister;
import com.igortbr.entitys.users.DTOUsersSearch;
import com.igortbr.entitys.users.Users;
import com.igortbr.infra.authentication.TokenService;
import com.igortbr.infra.exceptions.DTOExceptions;
import com.igortbr.model.books.RepositoryBooks;
import com.igortbr.model.users.Repository;
import com.igortbr.model.users_books.RepositoryUsersBooks;
import com.igortbr.services.auth.AuthorizationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Tag(name = "Autenticação", description = "Rotas de Autenticação")
public class Authentication {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private Repository repository;

	@Autowired
	private RepositoryBooks repositoryBooks;

	@Autowired
	private RepositoryUsersBooks repositoryUsersBooks;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthorizationService service;

	@PutMapping
	@Transactional
	public ResponseEntity update(@RequestBody DTOUserUpdate user, HttpServletRequest request) {
		Users userFindend = service.getUserByRequest(request);
		userFindend.update(user);

		return ResponseEntity.ok("Usuario atualizado com sucesso");
	}

	@PostMapping("/login")
	@Operation(summary = "Login", description = "Realizar Login utilizando, campo email e senha.")
	@ApiResponses({
			@ApiResponse(responseCode = "200 - Ok", description = "Sucesso, e a key JWT é enviaa para o usuario.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400 - Bad Request", description = "Quando os parametros estão fora do padrao.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401 - Unauthorized", description = "Quando o login não é encontrado.", content = @Content(mediaType = "application/json")) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Parametros solicitados pela Requisião")
	public ResponseEntity login(@RequestBody DTOUsersLogin user) {
		var emailPassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());

		var auth = this.authenticationManager.authenticate(emailPassword);
		var token = tokenService.generateToken((Users) auth.getPrincipal());

		return ResponseEntity.ok(token);
	}

	@PostMapping("/register")
	@Transactional
	@Operation(summary = "Register", description = "Realizar registro do usuario utilizando, campo Primeiro nome, Ultimo Nome, apelido, data de aniversario, email, senha e se usuario for administrador pode passar o cargo do usuario")
	@ApiResponses({
			@ApiResponse(responseCode = "200 - Ok", description = "Sucesso e a api retorna o usuario novo", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "409 - Conflict", description = "Indica que ja existe um email ou apelido registrado ou se algum campo essencial estiver vazio", content = @Content(mediaType = "application/json")) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Parametros solicitados pela Requisião")
	public ResponseEntity register(@RequestBody @Valid DTOUsersRegister user, HttpServletRequest request) {
		user = service.setRoleInUser(request, user);
		String validNewUser = service.validateUserRegister(user);
		if (!validNewUser.equals("")) {
			DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.BAD_REQUEST, validNewUser, "Not Finded");
			return ResponseEntity.status(DTOresponse.getCode()).body(DTOresponse);
		}
		Users newUser = new Users(user);
		this.repository.save(newUser);
		DTOExceptions DTOresponse = new DTOExceptions(HttpStatus.OK, "Usuario Criado com sucesso", "Not Finded");
		return ResponseEntity.status(DTOresponse.getCode()).body(DTOresponse);

	}

	@GetMapping
	@Transactional
	@Operation(summary = "Listar seu perfil", description = "Mosta o perfil do proprio usuario, baseado na key JWT dele")
	@ApiResponses({
			@ApiResponse(responseCode = "200 - Ok", description = "Sucesso e a api retorna o usuario", content = @Content(mediaType = "application/json")), })
	public ResponseEntity listMe(HttpServletRequest request) {
		Users user = service.getUserByRequest(request);
		return ResponseEntity.ok().body(new DTOUsersRead(user));
	}

	@GetMapping("/search")
	public ResponseEntity getBookByName(@RequestParam String nickname) {
		List<Users> books = repository.findByNicknameContainingAllIgnoreCaseAndEnabledTrue(nickname);

		List<DTOUsersSearch> myBooks = books.stream().map(DTOUsersSearch::new).toList();

		return ResponseEntity.ok().body(myBooks);

	}

	@GetMapping("/{uuid}")
	@Transactional
	@Operation(summary = "Listar o perfil de outro usuario baseado no seu uuid", description = "Mosta o perfil do proprio usuario, baseado no uuid dele")
	@ApiResponses({
			@ApiResponse(responseCode = "200 - Ok", description = "Sucesso e a api retorna o usuario", content = @Content(mediaType = "application/json")), })
	public ResponseEntity listForUUID(@PathVariable UUID uuid) {
		Users user = repository.findByIdAndEnabledTrue(uuid);
		return ResponseEntity.ok().body(new DTOUsersReadProfile(user));

	}

	@DeleteMapping
	public ResponseEntity delete(HttpServletRequest request) {
		Users user = service.getUserByRequest(request);
		return null;
	}

}
