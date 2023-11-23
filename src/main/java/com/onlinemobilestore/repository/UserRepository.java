package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    User findUserByEmail(String email);
    Boolean existsUserByPhoneNumber(String phoneNumber);
}