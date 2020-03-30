package org.example.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.example.message.InMsgEntity;
import org.example.message.OutMsgEntity;
import org.example.message.response.NewsMesEntity;
import org.example.message.response.TextMsgEntity;
import org.example.message.response.VodioMsgEntity;
import org.example.message.response.VoiceMsgEntity;
import org.example.util.MessageUtil;
import org.example.util.WeChatUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/wechat")
public class WechatSecurity {

    @GetMapping("/security")
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr) {
        try {
            log.info("微信验证调用开始 参数，sign:{},timestamp:{},nonce:{},echostr:{}",signature,timestamp,nonce,echostr);
            if (WeChatUtils.checkSignature(signature, timestamp, nonce)) {
                log.info("延签通过");
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                log.info("延签失败");
            }
        } catch (Exception e) {
            log.error("验证异常: e:{}",e);
        }
    }


    @PostMapping("/security")
    public String doSendMessage(@RequestBody InMsgEntity msg) {
        try {
            log.info("消息接收的参数为:{}", JSONObject.toJSONString(msg));
            OutMsgEntity out=null;
            //获取接收的消息类型
            String msgType = msg.getMsgType();
            if("text".equalsIgnoreCase(msgType)){
                TextMsgEntity entity=new TextMsgEntity();
                converMessage(entity,msg);
                entity.setContent(msg.getContent());
                String message = MessageUtil.getInstance().messageToXml(entity);
                log.info("消息响应用户的text信息为:{}",message);
                return message;
            }else if("Image".equalsIgnoreCase(msgType)){
                NewsMesEntity picMsgEntity=new NewsMesEntity();
                converMessage(picMsgEntity,msg);
                picMsgEntity.setMsgType("news");
                NewsMesEntity.Article article=new NewsMesEntity.Article();
                article.setDescription("这个是图文消息1");
                article.setPicUrl("https://i.loli.net/2019/05/26/5cea3d137aa1469348.jpg");
                article.setTitle("图文消息1");
                //图文连接
                article.setUrl("https://www.cnblogs.com/gede");
                List<NewsMesEntity.Article> list=new ArrayList();
                list.add(article);
                picMsgEntity.setArticleCount(list.size());
                picMsgEntity.setArticles(list);
                String message = MessageUtil.getInstance().messageToXml(picMsgEntity);
                log.info("消息响应用户的image信息为:{}",message);
                return message;
            }else if("voice".equalsIgnoreCase(msgType)){
                VoiceMsgEntity voiceMsgEntity=new VoiceMsgEntity();
                converMessage(voiceMsgEntity,msg);
                VoiceMsgEntity.Voice voice=new VoiceMsgEntity.Voice();
                voice.setMediaId(msg.getMediaId());
                voiceMsgEntity.setVoice(voice);
                String message = MessageUtil.getInstance().messageToXml(voiceMsgEntity);
                log.info("消息响应用户的voice信息为:{}",message);
                return message;
            }else if("video".equalsIgnoreCase(msgType)){
                VodioMsgEntity v=new VodioMsgEntity();
                converMessage(v,msg);
                VodioMsgEntity.Video video=new VodioMsgEntity.Video();
                video.setTitle(msg.getTitle());
                video.setDescription(msg.getDescription());
                video.setMediaId(msg.getMediaId());
                v.setVideo(video);
                String message = MessageUtil.getInstance().messageToXml(v);
                log.info("消息响应用户的video信息为:{}",message);
                return message;
            }else if("event".equalsIgnoreCase(msgType)){
               if("subscribe".equalsIgnoreCase(msg.getEvent())){
                   TextMsgEntity entity=new TextMsgEntity();
                   converMessage(entity,msg);
                   entity.setMsgType("text");
                   entity.setContent("欢迎您关注本公众号， 祝您玩的开心。。。。");
                   String message = MessageUtil.getInstance().messageToXml(entity);
                   log.info("消息响应用户的text信息为:{}",message);
                   return message;
               }
            }
        } catch (Exception e) {
            log.error("验证异常: e:{}", e);
        }
        return null;
    }



    private <E extends OutMsgEntity> E converMessage(E e, InMsgEntity msg){
        e.setToUserName(msg.getFromUserName());
        //把原来的接收方设置为发送方
        e.setFromUserName(msg.getToUserName());
        e.setMsgType(msg.getMsgType());
        //设置消息创建时间
        e.setCreateTime(new Date().getTime());
        return e;
    }


}
