package org.example.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsApiTicket implements Serializable {


    private String ticket;
    @JSONField(name="expires_in")
    private String expireIn;
}
