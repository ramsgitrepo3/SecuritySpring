package com.example.SecurirtySpring.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SecurirtySpring.entity.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
