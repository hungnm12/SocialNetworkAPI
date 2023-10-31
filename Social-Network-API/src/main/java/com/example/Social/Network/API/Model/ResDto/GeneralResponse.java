package com.example.Social.Network.API.Model.ResDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class GeneralResponse {

    //Gen success/fail code

    @Setter
    @JsonProperty("code")
    private Integer code;

    //Gen message

    @Setter
    @JsonProperty("message")
    private String message;

    //Gen data

    @Setter
    @JsonProperty("data")
    private Object data;


    public GeneralResponse(Integer code,  String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public GeneralResponse(Integer code, String message){
        this.code = code;
        this.message= message;
    }
    public GeneralResponse() {

    }


    public GeneralResponse(int httpNoContent, String s, String message, Object o) {
    }
}
