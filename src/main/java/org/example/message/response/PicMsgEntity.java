package org.example.message.response;

import lombok.Data;
import org.example.message.OutMsgEntity;

import java.io.Serializable;

@Data
public class PicMsgEntity extends OutMsgEntity {

    private Image Image;

    @Data
    public static class Image implements Serializable {

        private String MediaId;
    }


}
