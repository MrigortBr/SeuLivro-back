package com.igortbr.model.address;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igortbr.entitys.address.Address;

public interface RepositoryAddress extends JpaRepository<Address, UUID> {

}
