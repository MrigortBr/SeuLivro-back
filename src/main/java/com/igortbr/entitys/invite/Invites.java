package com.igortbr.entitys.invite;

import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.igortbr.entitys.books.Books;
import com.igortbr.entitys.invite.status.Status;
import com.igortbr.entitys.stores.Stores;
import com.igortbr.entitys.users.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "invite")
@Table(name = "invite")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Invites {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
    @ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id")
    private Books book;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public Invites(Books book) {
    	this.book = book;
    	this.status = Status.CREATED;
    }
    
    public String createLink() {
    	String code = this.id.toString();
    	this.status = Status.SAVED;
    	return Base64.getEncoder().encodeToString(code.getBytes());
    }
    
    public String loadLink(byte[] code) {
    	return new String(Base64.getDecoder().decode(code));
    }
}
