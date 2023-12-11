package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.BlockList;
import com.example.Social.Network.API.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockListRepo extends PagingAndSortingRepository<BlockList,Long>, JpaRepository<BlockList,Long> {


//    boolean existsBlockListsByUserAndUserIsBlocked(User user,User userIsBlock);
    @Query(value = "select bl.userIsBlocked from BlockList as bl where bl.user = ?1")
    List<User> getListBlockByUser(User user, Pageable pageable);

    boolean existsBlockListByUserAndUserIsBlocked(User user, User userIsBlocked);

    void deleteBlockListByUserAndUserIsBlocked(User user,User userIsBlocked);
}
