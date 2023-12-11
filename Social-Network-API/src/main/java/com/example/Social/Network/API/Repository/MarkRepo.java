package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Comment;
import com.example.Social.Network.API.Model.Entity.Mark;
import com.example.Social.Network.API.Model.Entity.Post;
import com.example.Social.Network.API.Model.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepo extends JpaRepository<Mark, Long>, PagingAndSortingRepository<Mark, Long> {

List<Mark> getMarksByPost(Post post,Pageable pageable);

boolean existsByUserMark(User user);
}
