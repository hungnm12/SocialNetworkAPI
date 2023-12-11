package com.example.Social.Network.API.Model.ResDto.PostResDto;

import com.example.Social.Network.API.Model.Entity.Post;
import jakarta.persistence.Embedded;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetListPostsResDto {
   @Embedded
   private Post post;

   private String new_items;
   private String last_id;



}

