package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Post;
import com.example.Social.Network.API.Model.ResDto.SearchResDto.SearchFunctionResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long>, PagingAndSortingRepository<Post,Long> {

Post findAllById(long Id);

@Query(value = "select p.described from User u inner join Post p on u.id = p.user.id " +
        "where p.described like %?2% "

)
    List<String> search(@Param("token") String token,
                                      @Param("keyword") String keyword,
                                      @Param("user_id") String Id,
                                      Pageable pageable
                                      );
}
