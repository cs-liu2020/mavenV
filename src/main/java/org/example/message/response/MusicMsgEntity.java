package org.example.message.response;

import lombok.Data;
import org.example.message.OutMsgEntity;

import java.io.Serializable;

@Data
public class MusicMsgEntity extends OutMsgEntity {

    private Music Music;

    /**
     * <Music>
     *     <Title><![CDATA[TITLE]]></Title>
     *     <Description><![CDATA[DESCRIPTION]]></Description>
     *     <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
     *     <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
     *     <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
     *   </Music>
     */
    @Data
    public static class Music implements Serializable {

        private String Title;

        private String Description;

        private String MusicUrl;

        private String HQMusicUrl;

        private String ThumbMediaId;
    }

}
