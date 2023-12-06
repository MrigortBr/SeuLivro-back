package com.igortbr.services.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.igortbr.entitys.users.DTOUsersRegister;
import com.igortbr.entitys.users.Users;
import com.igortbr.entitys.users.roles.RolesApp;
import com.igortbr.infra.authentication.SecurityFilter;
import com.igortbr.infra.authentication.TokenService;
import com.igortbr.model.users.Repository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	Repository repository;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private SecurityFilter securityFilter;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username);
	}
	
	public Users getUserByToken(String token) {
		Users user = (Users) repository.findByEmail(tokenService.validateToken(token));
		return user;
	}
	
	public String getToken(HttpServletRequest request) {
		return securityFilter.recoverToken(request);
	}
	
	public DTOUsersRegister setRoleInUser(HttpServletRequest request, DTOUsersRegister userRegister) {
		if (securityFilter.recoverToken(request) != null) {
			Users user = getUserByToken(securityFilter.recoverToken(request));
			if (user.getRole().ordinal() == 2) {
				userRegister = userRegister.setRole(RolesApp.USER);
			}
			
			if (user.getRole().ordinal() > userRegister.role().orElse(null).ordinal()) {
				userRegister = userRegister.setRole(user.getRole());
			}
		}else {
			userRegister = userRegister.setRole(RolesApp.USER);
		}
		
		return userRegister;
	}

	public Users getUserByRequest(HttpServletRequest request) {
		String token = getToken(request);
		return getUserByToken(token);
	}

    public String validateUserRegister(DTOUsersRegister user) {
        List<String> camposVazios = new ArrayList<>();

        if (user.birthDate().equals("")) {
            camposVazios.add("Data de aniversario");
        }

        if (user.firstName().equals("")) {
            camposVazios.add("Primeiro nome");
        }

        if (user.lastName().equals("")) {
            camposVazios.add("Segundo nome");
        }

        if (user.email().equals("")) {
            camposVazios.add("email");
        }

        if (user.identifier().equals("")) {
            camposVazios.add("CPF/CNPJ");
        }

        if (user.typeIdentifier().equals("")) {
            camposVazios.add("typeIdentifier");
        }

        if (user.password().equals("")) {
            camposVazios.add("Senha");
        }

        if (user.nickname().equals("")) {
            camposVazios.add("Apelido");
        }

        if (!camposVazios.isEmpty()) {
            return String.join(" e ", camposVazios) + " estão vazios.";
        }

        return "";
    }
}
