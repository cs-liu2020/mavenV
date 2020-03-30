package org.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.example.constant.WechatUrlConstant;
import org.example.listener.WebApplicationContextLocator;
import org.example.message.AccessToken;
import org.example.message.JsApiTicket;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class AccessTokenUtil {

    /**
     * @Author:yangwl
     * @date 2016年5月11日 下午5:25:03
     * @Description: //设置access_token
     */
    public static void initAndSetAccessToken() {
        log.info("execute initAndSetAccessToken Start : {}", System.currentTimeMillis());
        Properties prop = new Properties();
        try {
            InputStream in = AccessTokenUtil.class.getResourceAsStream("/application.properties");
            prop.load(in);
        } catch (IOException e) {
            log.info("execute initAndSetAccessToken {}", e.getMessage());
        }
        String appid = prop.getProperty("appId");
        String appsecret = prop.getProperty("secret");
        if(!appid.isEmpty() && !appsecret.isEmpty()) {
            AccessToken accessToken = getAccessToken(appid,appsecret);
            if(null != accessToken) {
                /**
                 * cache access_token
                 */
                ServletContext sc = WebApplicationContextLocator.get();
                sc.removeAttribute("access_token");
                log.info("查询获取的access_token为:{}",accessToken.getToken());
                sc.setAttribute("access_token", accessToken);

                /**
                 * cache jsapi_ticket
                 */
                JsApiTicket jsApiTicket = getJsApiTicket(accessToken.getToken());
                if(null != jsApiTicket) {
                    sc.removeAttribute("ticket");
                    log.info("查询获取的access_token为:{}",accessToken.getToken());
                    sc.setAttribute("ticket", jsApiTicket);
                }
                //这里可以向数据库中写access_token
            }
        } else {
            log.info("execute initAndSetAccessToken appid,appsecret 为空.{}");
        }
        log.info("execute initAndSetAccessToken End   : {}", System.currentTimeMillis());
    }


    /**
     * 获取access_token
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        log.info("getAccessToken 入参 appId:{},secret:{}",appid,appsecret);
        String url= WechatUrlConstant.GET_ACCESS_TOKEN+"?grant_type=client_credential" +"&appId="+appid+ "&secret="+appsecret ;
        String request = OkHttpUtils.doGetHttpsRequest(url, getHeaderMessage());
        JSONObject jsonObject = JSON.parseObject(request);
        // 如果请求成功
        if (request != url) {
            try {
               // {"access_token":"ACCESS_TOKEN","expires_in":7200}
                accessToken = JSON.parseObject(request, AccessToken.class);
                log.info("getAccessToken 转化后对象:{}",accessToken);
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", (int)jsonObject.get("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    public static AccessToken getAccessToken() {
        return (AccessToken) WebApplicationContextLocator.get().getAttribute("access_token");
    }


    public static JsApiTicket getJsApiTicket() {
        return (JsApiTicket) WebApplicationContextLocator.get().getAttribute("ticket");
    }


    public static Map<String,String> getHeaderMessage(){
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("content-type","application/json");
        return headerMap;
    }

    public static JsApiTicket getJsApiTicket(String accessToken) {
        JsApiTicket jsApiTicket = null;
        String url = String.format(WechatUrlConstant.GET_TICKET, accessToken);
        log.info("获取ticket的url为:{}",url);
        String request= OkHttpUtils.doGetHttpsRequest(url, getHeaderMessage());
        JSONObject jsonObject = JSON.parseObject(request);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                jsApiTicket = JSON.parseObject(request, JsApiTicket.class);
            } catch (JSONException e) {
                accessToken = null;
                // 获取jsApiTicket失败
                log.error("获取jsApiTicket失败 errcode:{} errmsg:{}", (int)jsonObject.get("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsApiTicket;
    }


}
