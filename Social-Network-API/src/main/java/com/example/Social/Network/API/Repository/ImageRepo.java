package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
    @Query("SELECT COUNT(i) FROM Image i WHERE i.post.Id = :postId")
    Long countByPostId(@Param("postId") Long Id);
}
