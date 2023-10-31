package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpRepo extends JpaRepository<UserEntity,Long> {

    boolean exisitedbyEmail(String email);


}
