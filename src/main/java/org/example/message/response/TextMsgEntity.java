package org.example.message.response;

import lombok.Data;
import org.example.message.OutMsgEntity;

@Data
public class TextMsgEntity extends OutMsgEntity {

    // 文本内容
    private String Content;
}
