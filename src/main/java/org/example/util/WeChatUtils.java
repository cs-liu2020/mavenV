package org.example.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.constant.WechatUrlConstant;
import org.example.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class WeChatUtils {

      @Autowired
      private ScheduledExecutorService scheduledExecutorService;

      public static String access_token;

      // 与接口配置信息中的 Token 要一致
       private static String token = "bmengz";

       /**
      * 验证签名
      * @param signature
      * @param timestamp
      * @param nonce
      * @return
      */
      public static boolean checkSignature(String signature, String timestamp, String nonce) {
          String[] arr = new String[] { token, timestamp, nonce };
          // 将 token、timestamp、nonce 三个参数进行字典序排序
          Arrays.sort(arr);
          StringBuilder content = new StringBuilder();
          for (int i = 0; i < arr.length; i++) {
              content.append(arr[i]);
          }
          MessageDigest md = null;
          String tmpStr = null;

          try {
                  md = MessageDigest.getInstance("SHA-1");
                  // 将三个参数字符串拼接成一个字符串进行 sha1 加密
                  byte[] digest = md.digest(content.toString().getBytes());
                  tmpStr = byteToStr(digest);
              } catch (NoSuchAlgorithmException e) {
                  e.printStackTrace();
              }

          content = null;
          // 将 sha1 加密后的字符串可与 signature 对比，标识该请求来源于微信
          return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
      }

      /**
      将字节数组转换为十六进制字符串
        @param byteArray
       @return
      **/
         private static String byteToStr(byte[] byteArray) {
             String strDigest = "";
           for (int i = 0; i < byteArray.length; i++) {
                   strDigest += byteToHexStr(byteArray[i]);
               }
             return strDigest;
         }

       /**
      将字节转换为十六进制字符串
       @param mByte
       @return
       **/
         private static String byteToHexStr(byte mByte) {
             char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
             char[] tempArr = new char[2];
             tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
             tempArr[1] = Digit[mByte & 0X0F];
             String s = new String(tempArr);
             return s;
         }


    // post方法用于接收微信服务端消息
    public  String getAccessToken(){

        String appId="wxec30c399b484f1ed";
        String secret= "4d48bdce8f3cc9631954ff9ca048d2ca";
        log.info("getAccessToken 入参 appId:{},secret:{}",appId,secret);
        String url= WechatUrlConstant.GET_ACCESS_TOKEN+"?grant_type=client_credential" +"&appId="+appId+ "&secret="+secret ;
        AtomicReference<String> request = null ;
        scheduledExecutorService.schedule(()->{
            log.info("异步调用获取access_token开始");
            request.set(OkHttpUtils.doGetHttpsRequest(url, getHeaderMessage()));
            log.info("调用微信AccessToken出参:{}",request);
            ResponseMessage responseMessage = JSON.parseObject(request.get(), ResponseMessage.class);
            if (StringUtils.isNotEmpty(responseMessage.getAccess_token())) {
                log.info("返回access_token:{},赋值",responseMessage.getAccess_token());
                access_token = responseMessage.getAccess_token();
            }
        },600, TimeUnit.SECONDS);
        return access_token;
    }

    private  static  Map<String,String> getHeaderMessage(){
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("content-type","application/json");
        return headerMap;
    }
}
