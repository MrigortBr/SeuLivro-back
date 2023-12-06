package com.igortbr.model.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.igortbr.entitys.users.DTOUsersSImple;
import com.igortbr.entitys.users.Users;


public interface Repository extends JpaRepository<Users, UUID>{
    @Query("SELECT u FROM user u WHERE u.enabled = TRUE ORDER BY RANDOM() LIMIT 3")
    List<Users> findRandomUsers();
    UserDetails findByEmail(String email);
    List<Users> findByNicknameContainingAllIgnoreCaseAndEnabledTrue(String name);
    Users findByIdAndEnabledTrue(UUID id);

}
