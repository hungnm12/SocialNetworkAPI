package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Comment;
import com.example.Social.Network.API.Model.Entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> , PagingAndSortingRepository<Comment, Long> {
    List<Comment> getCommentsByPost(Post post, Pageable pageable);
}
