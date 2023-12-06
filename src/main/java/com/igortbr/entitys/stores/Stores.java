package com.igortbr.entitys.stores;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.users.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "store")
@Table(name = "stores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data

public class Stores{
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", unique = true)
    private Users owner;
    private String cnpj;
    private String name;
    private String fantasyName;
    private String description;
	private boolean enabled;
	private String image;
	
	@JsonManagedReference
    @OneToMany(mappedBy = "store")
	private List<Books> books;

    
    
    public Stores(DTOStoreCreate newStore, Users owner) {
		this.owner = owner;
		this.cnpj = newStore.cnpj();
		this.name = newStore.name();
		this.fantasyName = newStore.fantasyName();
		this.description = newStore.description();
		this.enabled = newStore.enabled();
		this.image = newStore.image();
	}





}
