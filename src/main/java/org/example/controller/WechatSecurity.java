package org.example.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.example.message.InMsgEntity;
import org.example.message.OutMsgEntity;
import org.example.util.MessageUtil;
import org.example.util.WeChatUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

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
            //创建消息响应对象
            OutMsgEntity out = new OutMsgEntity();

            //把原来的发送方设置为接收方
            out.setToUserName(msg.getFromUserName());
            //把原来的接收方设置为发送方
            out.setFromUserName(msg.getToUserName());
            //获取接收的消息类型
            String msgType = msg.getMsgType();
            //设置消息的响应类型
            out.setMsgType(msgType);
            //设置消息创建时间
            out.setCreateTime(new Date().getTime());
            //根据类型设置不同的消息数据
            if ("text".equals(msgType)) {
                out.setContent(msg.getContent());
            } else if ("image".equals(msgType)) {
                out.setMediaId(new String[]{msg.getMediaId()});
            }
            String message = MessageUtil.getInstance().textMessageToXml(out);
            log.info("消息响应用户的信息为:{}",message);
            return message;

        } catch (Exception e) {
            log.error("验证异常: e:{}", e);
        }
        return null;
    }

}
