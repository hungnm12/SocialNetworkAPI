package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.UserEntity;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@Repository
public interface SignUpRepo extends JpaRepository<UserEntity,Long> {

    boolean existsByEmail(String email);


}
