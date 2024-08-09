package com.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecom.model.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    

    Optional<UserDtls> findByEmail(String email);

    Optional<UserDtls> findByUsername(String username);

    

}
