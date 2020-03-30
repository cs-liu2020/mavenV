package org.example.message.response;

import lombok.Data;
import org.example.message.OutMsgEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class VoiceMsgEntity extends OutMsgEntity {

    private Voice Voice;

    @Data
    public static class Voice implements Serializable {

        private String MediaId;
    }

}
