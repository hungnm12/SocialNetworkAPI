package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.ReqDto.ConversationReqDto.*;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.TokenRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.ConservationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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


    @Override
    public GeneralResponse getListConversation(GetListConversationReqDto getListConversationReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse getConversation(GetConversationReqDto getConversationReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse setReadMessage(SetReadMesageReqDto setReadMeesageReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse deleteMessage(DeleteMessageReqDto deleteMessageReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse deleteConversation(DeleteConversationReqDto deleteConversationReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }
}
