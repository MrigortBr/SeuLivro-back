package com.igortbr.entitys.users;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.igortbr.entitys.address.Address;
import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.identifier.Identifier;
import com.igortbr.entitys.users.roles.RolesApp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
	

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data

public class Users implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @Past
    private Date birthDate;
    @Email
    private String email;
    private String password;
    private RolesApp role;
	private boolean enabled;
	private String nickname; 
	private String identifier;
	private Identifier typeIdentifier;
	private String image;
	
	@JsonManagedReference
    @OneToMany(mappedBy = "author")
    private List<Books> books;
    
    @JsonManagedReference
    @OneToOne(mappedBy = "owner")
    private Stores store;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address;
    
    public Users(DTOUsersRegister newUser) {
    	
    	
    	this.firstName = newUser.firstName();
    	this.lastName = newUser.lastName();
    	this.birthDate = newUser.birthDate();
    	this.email = newUser.email();
    	this.password = new BCryptPasswordEncoder().encode(newUser.password());
        this.role = newUser.role().orElse(RolesApp.USER);
        this.nickname = newUser.nickname();
        this.identifier = newUser.identifier();
        this.typeIdentifier = newUser.typeIdentifier();
        this.image = newUser.image();
    }
    
    public void update(DTOUserUpdate user) {
    	
    	if (user.firstName() != null && !user.firstName().equals(this.firstName)) {
    		  this.firstName = user.firstName();
    		}

    		if (user.lastName() != null && !user.lastName().equals(this.lastName)) {
    		  this.lastName = user.lastName();
    		}

    		if (user.nickname() != null && !user.nickname().equals(this.nickname)) {
    		  this.nickname = user.nickname();
    		}

    		if (user.birthDate() != null && !user.birthDate().equals(this.birthDate)) {
    		  this.birthDate = user.birthDate();
    		}

    		if (user.email() != null && !user.email().equals(this.email)) {
    		  this.email = user.email();
    		}

    		if (user.password() != null && !new BCryptPasswordEncoder().matches(user.password(), this.getPassword())) {
    		  this.password = new BCryptPasswordEncoder().encode(user.password());
    		}

    		if (user.identifier() != null && !user.identifier().equals(this.identifier)) {
    		  this.identifier = user.identifier();
    		}

    		if (user.typeIdentifier() != null && !user.typeIdentifier().equals(this.typeIdentifier)) {
    		  this.typeIdentifier = user.typeIdentifier();
    		}

	        if (user.image() != null && !user.image().equals(this.image)) {
	            this.image = user.image();
	        }
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == RolesApp.OWNER) return List.of(new SimpleGrantedAuthority("ROLE_OWNER"), new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else if(this.role == RolesApp.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));  
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
