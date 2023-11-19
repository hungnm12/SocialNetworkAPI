package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.DelSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.GetSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.SearchFunctionReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface SearchService {
    GeneralResponse search(SearchFunctionReqDto searchFunctionReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getSavedSearch(GetSavedSearchReqDto getSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse delSavedSearch(DelSavedSearchReqDto delSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
}
