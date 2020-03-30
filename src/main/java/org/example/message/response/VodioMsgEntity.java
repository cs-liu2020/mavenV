package org.example.message.response;

import lombok.Data;
import org.example.message.OutMsgEntity;

import java.io.Serializable;

@Data
public class VodioMsgEntity extends OutMsgEntity {

    private Video Video;

    @Data
    public static class Video implements Serializable {

        private String MediaId;

        private String Title;

        private String Description;
    }

}
