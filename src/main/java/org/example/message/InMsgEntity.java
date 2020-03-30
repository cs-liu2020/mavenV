package org.example.message;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class InMsgEntity {


    // 开发者微信号
    @XmlElement(name="FromUserName")
    private String fromUserName;
    // 发送方帐号（一个OpenID）
    @XmlElement(name="ToUserName")
    private String toUserName;
    // 消息创建时间
    @XmlElement(name="CreateTime")
    private Long createTime;
    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * music 音乐消息
     */
    @XmlElement(name="MsgType")
    private String msgType;

    // 消息id
    @XmlElement(name="MsgId")
    private Long msgId;

    // 文本内容
    @XmlElement(name="Content")
    private String content;

    // 图片链接（由系统生成）
    @XmlElement(name="PicUrl")
    private String PicUrl;

    // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
    @XmlElement(name="MediaId")
    private String mediaId;

    //视频
    @XmlElement(name="thumbMediaId")
    private String ThumbMediaId;

    //语音
    @XmlElement(name="Format")
    private String format;

    @XmlElement(name="Recognition")
    private String recognition;


    //地理位置
    @XmlElement(name="Location_X")
    private String x;

    @XmlElement(name="Location_Y")
    private String y;

    //缩放大小
    @XmlElement(name="Scale")
    private String scale;

    //地理位置
    @XmlElement(name="Label")
    private String label;


    //连接
    @XmlElement(name="Title")
    private String title;
    @XmlElement(name="Description")
    private String description;
    @XmlElement(name="Url")
    private String url;

}