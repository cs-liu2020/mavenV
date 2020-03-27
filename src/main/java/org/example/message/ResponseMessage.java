package org.example.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseMessage implements Serializable {

    private String errcode;

    private String errmsg;

    private String access_token;

    private String expires_in;

}
