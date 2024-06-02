package com.chamoddulanjana.helloshoesapplicationsystem.repository;

import com.chamoddulanjana.helloshoesapplicationsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
