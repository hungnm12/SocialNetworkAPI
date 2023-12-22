package com.example.Social.Network.API.Model.ResDto.account_dto.check_new_item_res;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Version {
    private String version;
    private String required;
    private String url;

//    public Version(String version, String required,String url) {
//        this.version = version;
//        this.required=required;
//        this.url=url;
//    }
}
