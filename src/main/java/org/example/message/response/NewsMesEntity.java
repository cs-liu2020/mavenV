package org.example.message.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.example.message.OutMsgEntity;

import java.io.Serializable;
import java.util.List;

@Data
public class NewsMesEntity extends OutMsgEntity {

    //图文个数
    private Integer ArticleCount;

    private List<Article> Articles;

    @Data
    @XStreamAlias("item")
    public static class Article implements Serializable{

        private String Title;

        private String Description;

        private String PicUrl;

        private String Url;
    }

}
