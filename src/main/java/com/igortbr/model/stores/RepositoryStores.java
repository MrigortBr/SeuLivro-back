package com.igortbr.model.stores;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.igortbr.entitys.stores.Stores;


public interface RepositoryStores extends JpaRepository<Stores, UUID>{
    @Query("SELECT s FROM store s WHERE s.enabled = TRUE ORDER BY RANDOM() LIMIT 3 ")
    List<Stores> findRandomStores();
}
