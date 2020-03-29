package org.example.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccessToken implements Serializable {

    @JSONField(name = "access_token")
    private String token;

    @JSONField(name="expires_in")
    private String expireIn;
}
