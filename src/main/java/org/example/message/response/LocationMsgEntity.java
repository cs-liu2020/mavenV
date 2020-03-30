package org.example.message.response;

import lombok.Data;
import org.example.message.OutMsgEntity;

@Data
public class LocationMsgEntity extends OutMsgEntity {


    private String Location_X;

    private String Location_Y;

    //缩放大小
    private String Scale;

    //地理位置
    private String Label;
}
