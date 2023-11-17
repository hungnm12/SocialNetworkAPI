package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Token;
import com.example.Social.Network.API.Model.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token,Long> {
    Optional<Token> findTokenByToken(String token);
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);
//    @Query(Value = " ")
//    void updateAllByToken(String token);
    void deleteTokenByToken(String token);
    void deleteTokenByUserId(Long user_id);
}
