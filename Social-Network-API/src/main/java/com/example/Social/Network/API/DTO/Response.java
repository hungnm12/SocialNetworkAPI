package com.example.Social.Network.API.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class Response {
    @Getter
    @Setter
    @JsonProperty
    private Integer code;

    @Getter
    @Setter
    @JsonProperty
    private String msg;

    @Getter
    @Setter
    @JsonProperty
    private Object data;

    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
