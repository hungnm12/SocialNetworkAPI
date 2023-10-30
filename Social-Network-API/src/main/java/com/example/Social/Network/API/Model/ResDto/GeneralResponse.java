package com.example.Social.Network.API.Model.ResDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class GeneralResponse {

    //Gen success/fail code

    @Getter
    @Setter
    @JsonProperty("code")
    private Integer code;

    //Gen message

    @Getter
    @Setter
    @JsonProperty("message")
    private String message;

    //Gen data

    @Getter
    @Setter
    @JsonProperty("data")
    private Object data;


    public GeneralResponse(Integer code,  String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public GeneralResponse() {

    }




}
