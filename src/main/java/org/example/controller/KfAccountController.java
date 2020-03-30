package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.constant.WechatUrlConstant;
import org.example.util.AccessTokenUtil;
import org.example.util.OkHttpUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 客服服务
 */
@RestController
@Log4j2
@RequestMapping("/kf")
public class KfAccountController {

    @RequestMapping("/create")
    public String createKfAccout(@RequestBody String body){
        log.info("添加客服的入参为:{}",body);
       String url=  WechatUrlConstant.CREATE_KF+"?access_token="+AccessTokenUtil.getAccessToken().getToken();
       String request = OkHttpUtils.doPostHttpsRequest(url, body,getHeaderMessage());
       log.info("添加客服的出参为:{}",request);
       return request;
    }

    @RequestMapping("/sendMessage")
    public String sendMessage(@RequestBody String body){
        /**
         * {
         *     "touser":"OPENID",
         *     "msgtype":"text", //类型
         *     "text":
         *     {
         *          "content":"Hello World"
         *     }
         * }
         *
         * {
         *     "touser":"oWhdN018l-xELx62XadQqHSQL5fg",
         *     "msgtype":"news",
         *     "news":{
         *         "articles": [
         *          {
         *              "title":"图文消息1",
         *              "description":"这个是图文消息1",
         *              "url":"http://www.baidu.com",
         *              "picurl":"https://i.loli.net/2019/05/26/5cea3d137aa1469348.jpg"
         *          }
         *          ]
         *     }
         * }
         */
        log.info("发送消息的入参为:{}",body);
        String url=  WechatUrlConstant.SEND_MESSAGE+"?access_token="+AccessTokenUtil.getAccessToken().getToken();
        String request = OkHttpUtils.doPostHttpsRequest(url, body,getHeaderMessage());
        log.info("发送消息的出参为:{}",request);
        return request;
    }



    private Map<String,String> getHeaderMessage(){
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("content-type","application/json");
        return headerMap;
    }
}
