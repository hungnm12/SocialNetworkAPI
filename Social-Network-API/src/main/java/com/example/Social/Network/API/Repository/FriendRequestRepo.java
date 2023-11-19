package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.FriendList;
import com.example.Social.Network.API.Model.Entity.FriendRequest;
import com.example.Social.Network.API.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Repository
public interface FriendRequestRepo extends JpaRepository<FriendRequest,Long>, PagingAndSortingRepository<FriendRequest,Long> {
     @Query(value = "select  count (*) from FriendRequest fr where fr.userSendRequest = ?1 ")
     long countUsersGetRequestByUserSendRequest(User userGetRequest);

     void deleteFriendRequestByUserGetRequestAndUserSendRequest(User userGetRequest , User userSendRequest);

     boolean existsFriendRequestByUserGetRequestAndUserSendRequest(User userGetRequest, User userSendRequest);

     @Query(value = "select fr.userGetRequest from FriendRequest as fr where fr.userSendRequest.id = ?1")
     List<User> findAllRequestFriendByTheUserId(Long userSendRequestId, Pageable pageable);

     FriendRequest findFriendRequestByUserSendRequestAndUserGetRequest(User userSendRequest,User userGetRequest);

}
