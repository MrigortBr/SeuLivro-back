package com.igortbr.entitys.address;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.invite.Invites;
import com.igortbr.entitys.invite.status.Status;
import com.igortbr.entitys.users.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "address")
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

    @JsonManagedReference
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Users user;
    
    private String streetAddress;
    private String city;
    private String acronymState;
    private String postalCode;
    private String country;
    private String reference;
    private String name;
    
	public Address(Users user, DTOAddressCreate newAddress) {
		super();
		this.user = user;
		this.streetAddress = newAddress.streetAddress();
		this.city = newAddress.city();
		this.acronymState = newAddress.acronymState();
		this.postalCode = newAddress.postalCode();
		this.country = newAddress.country();
		if (newAddress.reference() == null || newAddress.reference().equals("")) {
			this.reference = "Sem referencia";
		}else {
			this.reference = newAddress.reference();
		}
		this.name = newAddress.name();
	}
	
	public void update(DTOAddresUpdate newAddress) {
		if (newAddress.streetAddress() != null && !newAddress.streetAddress().equals(this.streetAddress)) {
		    this.streetAddress = newAddress.streetAddress();
		}

		if (newAddress.city() != null && !newAddress.city().equals(this.city)) {
		    this.city = newAddress.city();
		}

		if (newAddress.acronymState() != null && !newAddress.acronymState().equals(this.acronymState)) {
		    this.acronymState = newAddress.acronymState();
		}

		if (newAddress.postalCode() != null && !newAddress.postalCode().equals(this.postalCode)) {
		    this.postalCode = newAddress.postalCode();
		}

		if (newAddress.country() != null && !newAddress.country().equals(this.country)) {
		    this.country = newAddress.country();
		}

		if (newAddress.reference() != null && !newAddress.reference().equals(this.reference)) {
		    this.reference = newAddress.reference();
		}
		
		if (newAddress.name() != null && !newAddress.name().equals(this.name)) {
		    this.name = newAddress.name();
		}

	}
    
    
    
}
