package com.igortbr.model.invite;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.igortbr.entitys.invite.Invites;
import com.igortbr.entitys.invite.status.Status;


public interface RepositoryInvite extends JpaRepository<Invites, UUID>{
	@Query("SELECT i FROM invite i WHERE i.book.id = :idBook AND (i.status IS NULL OR i.status != :status)")
	List<Invites> findByBookIdAndStatusNot(@Param("idBook") UUID idBook, @Param("status") Status status);


}
