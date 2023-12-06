package com.igortbr.controllers.address;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igortbr.entitys.address.Address;
import com.igortbr.entitys.address.DTOAddresRead;
import com.igortbr.entitys.address.DTOAddresUpdate;
import com.igortbr.entitys.address.DTOAddressCreate;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.Users;
import com.igortbr.infra.exceptions.UsersResponses;
import com.igortbr.model.address.RepositoryAddress;
import com.igortbr.model.users.Repository;
import com.igortbr.services.auth.AuthorizationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

	@Autowired
	private AuthorizationService service;

	@Autowired
	private RepositoryAddress repository;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity create(@RequestBody DTOAddressCreate address, HttpServletRequest request) throws IOException {
		Users user = service.getUserByRequest(request);
		Address newAddres = new Address(user, address); 
		repository.save(newAddres);
		return UsersResponses.createOkRequest("Endereço Cadastrado com sucesso\"");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest request) throws IOException {
		Users user = service.getUserByRequest(request);
		Optional<Address> address = repository.findById(id);
		if (address.get() != null) {
			if (address.get().getUser().getId() == user.getId()) {
				try {
					repository.delete(address.get());
				} catch (Exception e) {
					user.setAddress(null);
					address.get().setUser(null);
				}
				
				return UsersResponses.createOkRequest("Endereço deletado com sucesso");
			}
		}
		return UsersResponses.createNotFoundRequest("Endereço não deletado pois nao encontrado, reinicie a pagina e tente novamente");
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity update(@RequestBody DTOAddresUpdate addressNew, HttpServletRequest request) throws IOException {
		Users user = service.getUserByRequest(request);
		Optional<Address> addressOld = repository.findById(addressNew.id());
		System.out.println(addressNew);
		if (addressOld.get() != null) {
			if (addressOld.get().getUser().getId() == user.getId()) {
				addressOld.get().update(addressNew);
				return UsersResponses.createOkRequest("Endereço editado com sucesso");
			}
		}
		return UsersResponses.createNotFoundRequest("Erro ao editar usuario");
	}
	
	@GetMapping
	public ResponseEntity readMyAddress(HttpServletRequest request) {
		List<DTOAddresRead> address = service.getUserByRequest(request).getAddress().stream().map(DTOAddresRead::new).toList();
		return ResponseEntity.ok().body(address);
	}
	
}
