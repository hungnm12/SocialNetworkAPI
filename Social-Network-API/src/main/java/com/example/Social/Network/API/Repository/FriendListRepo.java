package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.FriendList;
import com.example.Social.Network.API.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendListRepo extends PagingAndSortingRepository<FriendList,Long>,JpaRepository<FriendList,Long> {

    @Query(value = "select  fl.userIdFriend from FriendList  as fl where fl.userId.id = ?1")
    List<User> findUserFriendByTheUserId(Long userId, Pageable pageable);

    @Query(value = "select  fl.userIdFriend from FriendList  as fl where fl.userId.id = ?1")
    List<User> findUserFriendByTheUserIdNotPageable(Long userId);

    Optional<FriendList> findFriendListByUserIdAndUserIdFriend(User userId, User userIdFriend);
    @Query( value = "select count (*) from FriendList   as fl " +
            " inner join FriendList as fl2 on fl.userIdFriend= fl2.userIdFriend " +
            "where fl.userId.id=?1 and fl2.userId.id=?2")
    long findSameFiends(Long userId, Long userIdFriend);

    @Query(value = "select count(fl) from FriendList fl where fl.userId.id = fl.userIdFriend.id")
    long isFriend(Long userId, Long friendId);
}
