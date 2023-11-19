package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsUserById(Long id);
    Optional<User> findByEmail(String email);
    @NotNull Optional<User> findById(@NotNull Long id);
//    void updateUserByEmail(String email);


}
