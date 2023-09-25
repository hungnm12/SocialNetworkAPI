package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.DTO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmailAddress(String email);
}
