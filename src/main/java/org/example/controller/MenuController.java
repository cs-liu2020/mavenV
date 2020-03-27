package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.constant.WechatUrlConstant;
import org.example.util.OkHttpUtils;
import org.example.util.WeChatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/menu")
public class MenuController {

    @RequestMapping("/create")
    public String createMenu(@RequestParam(value = "menuList") String menuList){
        log.info("创建菜单的入参为{}",menuList);
        String url= WechatUrlConstant.CREATE_MENU+"?access_token="+ WeChatUtils.access_token;
        String httpsRequest = OkHttpUtils.doPostHttpsRequest(url, menuList, getHeaderMessage());
        log.info("创建菜单出参为:{}",httpsRequest);
        return httpsRequest;
    }

    @RequestMapping("/searchMenu")
    public String searchMenu(){
        String url= WechatUrlConstant.SEARCH_MENU+"?access_token="+ WeChatUtils.access_token;
        String httpsRequest = OkHttpUtils.doGetHttpsRequest(url, getHeaderMessage());
        log.info("查询菜单出参为:{}",httpsRequest);
        return httpsRequest;
    }

    private Map<String,String> getHeaderMessage(){
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("content-type","application/json");
        return headerMap;
    }
}
