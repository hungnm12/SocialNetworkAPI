package com.example.Social.Network.API.Model.ResDto.PostResDto;

import com.example.Social.Network.API.Model.Entity.Post;
import jakarta.persistence.Embedded;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetListPostsResDto {
   @Embedded
   private Post post;

   private String new_items;
   private String last_id;

}
