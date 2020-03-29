package org.example.controller;


import lombok.extern.log4j.Log4j2;
import org.example.util.WeChatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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

}
