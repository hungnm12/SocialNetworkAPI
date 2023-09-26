package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.DTO.SignUpDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignUpDTORepository extends JpaRepository<SignUpDTO,String> {
    Optional<SignUpDTO> findByEmailAddress(String email);
}
