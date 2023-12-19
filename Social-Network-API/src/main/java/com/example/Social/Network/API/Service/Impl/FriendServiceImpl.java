package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.BlockList;
import com.example.Social.Network.API.Model.Entity.FriendList;
import com.example.Social.Network.API.Model.Entity.FriendRequest;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Model.ResDto.block_res_dto.GetListBlockResDto;
import com.example.Social.Network.API.Model.ResDto.friend_res_dto.request_friend_res_dto.GetRequestFriendRes;
import com.example.Social.Network.API.Model.ResDto.friend_res_dto.request_friend_res_dto.GetRequestFriendResDetailDto;
import com.example.Social.Network.API.Model.ResDto.friend_res_dto.request_friend_res_dto.SetFriendRequestRes;
import com.example.Social.Network.API.Model.ResDto.friend_res_dto.user_friend_res_dto.GetUseFriendsResDetailDto;
import com.example.Social.Network.API.Model.ResDto.friend_res_dto.user_friend_res_dto.GetUserFriendsResDto;
import com.example.Social.Network.API.Repository.*;
import com.example.Social.Network.API.Service.FriendServiceI;
import com.example.Social.Network.API.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Transactional
public class FriendServiceImpl implements FriendServiceI {

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final JwtService jwtService;


    @Autowired
    private FriendListRepo friendListRepo;
    @Autowired
    private final FriendRequestRepo friendRequestRepo;

    @Autowired
    private final BlockListRepo blockListRepo;
    private final S3Service s3Service;
    public FriendServiceImpl(SignUpRepo signUpRepo, UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, FriendRequestRepo friendRequestRepo, BlockListRepo blockListRepo, S3Service s3Service) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.friendRequestRepo = friendRequestRepo;
        this.blockListRepo = blockListRepo;
        this.s3Service = s3Service;
    }
// yêu cầu kết bạn từ người chủ tài khoản đến người dùng nào đó.
    @Override
    public GeneralResponse setRequestedFriend(String token, Long userId) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }

        if (token.isEmpty() || userId == null) {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The parameter is not enough");

        }
        var userSendRequest = JwtUtils.getUserFromToken(jwtService, userRepo, token);
        User userGetRequest;
        try {
            userGetRequest = userRepo.findById(userId).orElseThrow();

        } catch (Exception e) {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The user id does not exists or not valid");

        }
        if(userId.equals(userSendRequest.getId()))
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The user id is not valid");

        }
        var numberOfFriends  = friendRequestRepo.countUsersGetRequestByUserSendRequest(userSendRequest);
//        Delete the request duplicate
        if(friendRequestRepo.existsFriendRequestByUserGetRequestAndUserSendRequest(userGetRequest,userSendRequest))
        {
            friendRequestRepo.deleteFriendRequestByUserGetRequestAndUserSendRequest(userGetRequest,userSendRequest);
            return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, SetFriendRequestRes.builder().request_friend(numberOfFriends-1).build() );

        }
        var friendRequest = FriendRequest.builder().userSendRequest(userSendRequest).userGetRequest(userGetRequest).createdTime(new Date(System.currentTimeMillis())).build();
        friendRequestRepo.save(friendRequest);

        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, SetFriendRequestRes.builder().request_friend(friendRequestRepo.countUsersGetRequestByUserSendRequest(userSendRequest)).build() );
    }

    @Override
    public GeneralResponse setAcceptFriend(String token, Long userId, String isAccept) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }

        if(token.isEmpty() || isAccept.isEmpty())
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The parameter is not enough");

        }
        else if(userId == null ||( !isAccept.equals("0")  && !isAccept.equals("1"))  )
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The parameter is not enough");

        }
        var accountRequest = JwtUtils.getUserFromToken(jwtService,userRepo, token);

        if(!userRepo.existsUserById(userId) ||accountRequest==null )
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The user does not exists or not valid");

        }
        var userGetRequest = userRepo.findById(userId).get();

//        Da chap nhan hoac xoa yeu cau ket ban r
        var friendRequest = friendRequestRepo.existsFriendRequestByUserGetRequestAndUserSendRequest(userGetRequest,accountRequest);
        if(!friendRequest)
        {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE,ResponseMessage.ACTION_BEEN_DONE_PRE,"Action has been done previously by this user");
        }
//        accept new friend
        if(isAccept.equals("1")){
            System.out.println("dat");
           friendListRepo.save(FriendList.builder().userId(accountRequest).userIdFriend(userGetRequest).createdTime(new Date(System.currentTimeMillis())).build());

        }
        System.out.println("heloooooooooooooooooooo");
//         when  reject or accept friend request  => delete friend request
        friendRequestRepo.deleteFriendRequestByUserGetRequestAndUserSendRequest(userGetRequest, accountRequest);

        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, "Ok" );

    }

    @Override
    public GeneralResponse setBlock(String token, Long userId, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }
        if(token.isEmpty() || userId == null ||(!type.equals("0") && !type.equals("1")))
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The parameter is not enough or not valid");

        }
        var isBlockedUser = userRepo.findById(userId);

        if( isBlockedUser.isEmpty() )
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The user does not exists");

        }
        if( user.equals(isBlockedUser.get()))
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The user id is equal the user");

        }
        if(type.equals("0"))
        {
//            Neu da bi block roi thi thong bao da bi block
            var isBlock = blockListRepo.existsBlockListByUserAndUserIsBlocked(user,isBlockedUser.get());
            if(isBlock)
            {
                return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE, "The user already has been blocked");

            }
            var blockList =  BlockList.builder().user(user).userIsBlocked(isBlockedUser.get()).createAt(new Date(System.currentTimeMillis())).build();

            blockListRepo.save(blockList);
            return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, "Block success" );


        }
//         Yeu cau unblock

        var isBlock = blockListRepo.existsBlockListByUserAndUserIsBlocked(user,isBlockedUser.get());
//        Neu khong ton tai nhma lai yeu cau unblock
        if(!isBlock)
        {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE, "The user already has been blocked");

        }
        blockListRepo.deleteBlockListByUserAndUserIsBlocked(user,isBlockedUser.get());


        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, "UnBlock success" );
    }

    @Override
    public GeneralResponse getListBlocks(String token, Integer index, Integer count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        if(token.isEmpty() || index == null || count == null)
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The parameter is not enough or not valid");

        }
        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }
        var currentUser = JwtUtils.getUserFromToken(jwtService,userRepo, token);

        if(currentUser ==null || !userRepo.existsUserById(currentUser.getId()))
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The user does not valid");

        }
        Pageable pageable =  PageRequest.of(index,count);
        List<User> blockListUser =  blockListRepo.getListBlockByUser(currentUser,pageable);
        List<GetListBlockResDto> getListBlockResDto = new ArrayList<>();
        for(User u : blockListUser){

            GetListBlockResDto x = GetListBlockResDto.builder().id(u.getId()).username(u.getUserNameAccount()).avatar(u.getAvatar()).build();
            getListBlockResDto.add(x);
        }

        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, getListBlockResDto);

    }

    @Override
    public GeneralResponse getListSuggestedFriends(String token, Integer index, Integer count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }
        if(index ==null|| count==null){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The parameter is not valid");

        }
        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,   "");

    }

    @Override
    public GeneralResponse getRequestedFriend(String token,Integer index , Integer count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        if(token.isEmpty() || index ==null || count == null)
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The parameter is not enough or not valid");

        }

        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }
        Pageable paging = PageRequest.of(index,count);
        List<User> friendRequest = friendRequestRepo.findAllRequestFriendByTheUserId(user.getId(),paging);
        ArrayList<GetRequestFriendResDetailDto> getRequestFriendResDetailDtoArrayList = new ArrayList<>();
        for(User u : friendRequest)
        {
            System.out.println(u);
//             friendRequestRepo.findFriendRequestByUserSendRequestAndUserGetRequest(user,u),
            GetRequestFriendResDetailDto x = new GetRequestFriendResDetailDto(u.getId(),u.getUserNameAccount(),u.getAvatar(),friendListRepo.findSameFiends(user.getId(), u.getId())  ,friendRequestRepo.findFriendRequestByUserSendRequestAndUserGetRequest(user,u).getCreatedTime() );
            getRequestFriendResDetailDtoArrayList.add(x);
        }

        Long totalRequestFriend = friendRequestRepo.countUsersGetRequestByUserSendRequest(user);

        // Need to add the logic find the same friend when i have finished get the list friend
        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,new GetRequestFriendRes(getRequestFriendResDetailDtoArrayList ,totalRequestFriend));

    }





    @Override
    public GeneralResponse getUserFriends(Long userId, String token, Integer index, Integer count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        if(token.isEmpty() || index == null || count == null )
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The parameter is not enough");

        }
        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }
        var currentUser = JwtUtils.getUserFromToken(jwtService, userRepo, token);

        if(userId == null)
        {
            userId = currentUser.getId();
        }
        if(userRepo.findById(userId).isEmpty()){
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The user id does not exists or not valid");

        }
        Pageable paging =  PageRequest.of(index,count);
        List<User> listFriends =  friendListRepo.findUserFriendByTheUserId(userId, paging);
        ArrayList<GetUseFriendsResDetailDto> listFriendsConvert = new ArrayList<>();
        for (User listFriend : listFriends) {
            var check =  friendListRepo.findFriendListByUserIdAndUserIdFriend(userRepo.findById(userId).get(), listFriend);
            if (check.isPresent())
            {
                Date createAt =check.get().getCreatedTime();
                var u = new GetUseFriendsResDetailDto(listFriend.getId(), listFriend.getUserNameAccount(), listFriend.getAvatar(), friendListRepo.findSameFiends(currentUser.getId(), listFriend.getId()), createAt);
                listFriendsConvert.add(u);
            }


        }

        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,new GetUserFriendsResDto( listFriendsConvert, listFriends.size() ));

    }



    @Override
    public GeneralResponse getListVideos() throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

}
