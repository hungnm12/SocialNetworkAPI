package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.ConversationReqDto.*;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface ConservationService {

    GeneralResponse getListConversation(GetListConversationReqDto getListConversationReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getConversation(GetConversationReqDto getConversationReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse setReadMessage(SetReadMesageReqDto setReadMeesageReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse deleteMessage(DeleteMessageReqDto deleteMessageReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse deleteConversation(DeleteConversationReqDto deleteConversationReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;


}
