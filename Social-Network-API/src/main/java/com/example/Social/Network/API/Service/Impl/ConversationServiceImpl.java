package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.Conversation;
import com.example.Social.Network.API.Model.Entity.Partner;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.ReqDto.ConversationReqDto.*;
import com.example.Social.Network.API.Model.ResDto.ConversationResDto.GetListConversationResDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.ConversationRepo;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.TokenRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.ConservationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.example.Social.Network.API.utils.JwtUtils.getUserFromToken;

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
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ConversationRepo conversationRepo;


    @Override
    public GeneralResponse getListConversation(GetListConversationReqDto getListConversationReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {


        User user = getUserFromToken(jwtService,userRepo, getListConversationReqDto.getToken());
        if(user==null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if (!jwtService.isTokenValid(getListConversationReqDto.getToken() , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }
        Conversation conversation = new Conversation();
        conversation.setUser(user);

        GetListConversationResDto getListConversationResDto = new GetListConversationResDto();
        getListConversationResDto.setId(String.valueOf(conversation.getId()));

        User partner = conversation.getUser();
        Partner partner1 = new Partner();
        partner1.setId(String.valueOf(partner.getId()));
        partner1.setUsername(partner.getUsername());
        partner1.setAvatar(partner.getAvatar());
        getListConversationResDto.setPartner(partner1);


        Pageable paging = PageRequest.of(getListConversationReqDto.getIndex(),getListConversationReqDto.getCount());

        List<Conversation> conversationList = conversationRepo.getListConversation(paging);


        return new GeneralResponse();
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
