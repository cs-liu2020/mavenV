package org.example.controller;


import lombok.extern.log4j.Log4j2;
import org.example.constant.WechatUrlConstant;
import org.example.util.OkHttpUtils;
import org.example.util.WeChatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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


    @RequestMapping("/getApiIp")
    // post方法用于接收微信服务端消息
    public String getApiIp() {
        String access_token = WeChatUtils.access_token;
        log.info("getApiIp 入参 accesstoken:{}");
        String url= WechatUrlConstant.GET_API_IP+"?access_token="+ access_token;
        log.info("getApiIp 调用微信入参:{}",url);
        return OkHttpUtils.doGetHttpsRequest(url, getHeaderMessage());
    }

    private Map<String,String> getHeaderMessage(){
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("content-type","application/json");
        return headerMap;
    }
}
