package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.DelSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.GetSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.SearchFunctionReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.ImageRepo;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private S3Service s3Service;
    @Autowired
    private UserRepo userRepo;
    @Override
    public GeneralResponse search(SearchFunctionReqDto searchFunctionReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        Sort sort = null;
        if (!StringUtils.isEmpty(searchFunctionReqDto.getSortBy()) && !StringUtils.isEmpty(searchFunctionReqDto.getIndex())) {
            Sort.Order order = searchFunctionReqDto.getIndex().equals("asc") ?
                    Sort.Order.asc(searchFunctionReqDto.getSortBy()) : Sort.Order.desc(searchFunctionReqDto.getSortBy());

            sort = Sort.by(order);
        }
        else {
            sort = Sort.by(
                    Sort.Order.asc("")
            );
        }

        int count = 0;
        if (!StringUtils.isEmpty(searchFunctionReqDto.getCount()) && searchFunctionReqDto.getCount() > 0) {
            count = searchFunctionReqDto.getCount() - 1;
        }



        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,"");
    }

    @Override
    public GeneralResponse getSavedSearch(GetSavedSearchReqDto getSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse delSavedSearch(DelSavedSearchReqDto delSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }
}
