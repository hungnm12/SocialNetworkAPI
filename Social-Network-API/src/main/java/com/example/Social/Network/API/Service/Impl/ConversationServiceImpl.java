package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.TokenRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.ConservationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class ConversationServiceImpl implements ConservationService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenRepo tokenRepo;
}
