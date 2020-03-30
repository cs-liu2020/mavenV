package org.example;

public enum  MsgTypeEnum {
    /**
     *  返回消息类型：文本
     */
    RESP_MESSAGE_TYPE_TEXT("text"),
    /**
     * 返回消息类型：音乐
     */
    RESP_MESSAGE_TYPE_MUSIC ("music"),

    //图文
    RESP_MESSAGE_TYPE_NEWS ("news"),

    //图片
    REQ_MESSAGE_TYPE_IMAGE("image"),

    //连接
    REQ_MESSAGE_TYPE_LINK("link"),

    //地理位置
    REQ_MESSAGE_TYPE_LOCATION("location"),

    //音频
    REQ_MESSAGE_TYPE_VOICE("voice"),

    //事件
    REQ_MESSAGE_TYPE_EVENT ("event"),

    //订阅
    EVENT_TYPE_SUBSCRIBE("subscribe"),

    //取消订阅
    EVENT_TYPE_UNSUBSCRIBE("unsubscribe"),

    //点击
    EVENT_TYPE_CLICK ("CLICK");

    MsgTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
