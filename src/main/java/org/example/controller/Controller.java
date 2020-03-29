package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class Controller {


    @RequestMapping(value = "/test")
    public String getMessage(){
        log.info("aaaaaaaaaaaaaaaaaaaa,方法:{}");
        return "hello world, opay............test";

    }

    @RequestMapping(value = "/check")
    public String checkMessage(){
        log.info("checkMessage,方法:{}");
        return "check wechat is ok..";
    }

    @RequestMapping(value = "/view")
    public String viewMessage(){
        log.info("viewMessage,方法:{}");
        return "viewMessage  is ok..";
    }

    @RequestMapping(value = "/wechat")
    public String wechatMessage(){
        return "wechatMessage wechat is ok..";
    }


    @RequestMapping(value = "/localTest")
    public String localTest(){
        log.info("aaaaaaaaaaaaaaaaaaaa,方法:{}");
        return "hello world, opay............test";

    }


}
