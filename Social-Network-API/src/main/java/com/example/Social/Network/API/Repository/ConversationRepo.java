package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Conversation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepo extends JpaRepository<Conversation,Long> {

    @Query(
            "select c from  User u inner join Conversation  c on u.id = c.user.id"
    )
    List<Conversation> getListConversation(Pageable pageable);
}
