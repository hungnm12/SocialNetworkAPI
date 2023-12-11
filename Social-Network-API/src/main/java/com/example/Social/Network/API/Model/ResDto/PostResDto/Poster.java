package com.example.Social.Network.API.Model.ResDto.PostResDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Poster {
    Long id;
    String name;
    String avatar;
}
