package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.DTO.SignUpDTO;
import com.example.Social.Network.API.Service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class SignUpController {

    @Autowired
    SignupService signupService;

    @PostMapping
    public ResponseEntity SignUp(@RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok().build();
    }
}
