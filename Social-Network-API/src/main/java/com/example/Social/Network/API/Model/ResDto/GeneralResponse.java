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
    private String code;

    //Gen message

    @Setter
    @JsonProperty("message")
    private String message;

    //Gen data

    @Setter
    @JsonProperty("data")
    private Object data;

    @Setter
    @JsonProperty("num_new_message")
    private Long numNewMessage;


    public GeneralResponse(String code,  String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public GeneralResponse(String code, String message){
        this.code = code;
        this.message= message;
    }
    public GeneralResponse() {

    }

    public GeneralResponse(String code, String message, Object data, Long numNewMessage) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.numNewMessage = numNewMessage;

    }

    public GeneralResponse(int httpNoContent, String s, String message, Object o) {
    }
}
