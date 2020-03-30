package org.example.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.example.constant.WechatUrlConstant;
import org.example.util.AccessTokenUtil;
import org.example.util.NewsUtil;
import org.example.util.OkHttpUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片
 */
@RestController
@Log4j2
@RequestMapping("/news")
public class NewsController {

    @RequestMapping(value = "/uploadImg")
    public Object uploadImg(@RequestBody  String filePath) {
        log.info("上传图片入参的路径:{}",filePath);
        String filePath1 = JSONObject.parseObject(filePath).getString("filePath");
        String result = NewsUtil.uploadimg(filePath1);
        log.info("uploadImg上传图片结果：resut:{}",result);
        return result;
    }

    @RequestMapping(value = "/sendTemplate")
    public String sendTemplate(@RequestBody  String body) {
        log.info("发送模板的入参:{}",body);
        String url= WechatUrlConstant.SEND_TEMPLATE+"?access_token="+ AccessTokenUtil.getAccessToken().getToken();
        String request = OkHttpUtils.doPostHttpsRequest(url, body, getHeaderMessage());
        log.info("发送模板的出参结果：resut:{}",request);
        return request;
    }

    private Map<String,String> getHeaderMessage(){
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("content-type","application/json");
        return headerMap;
    }
}

