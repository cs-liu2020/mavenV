package org.example.util;

import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
public class OkHttpUtils {

    private static final String MDC_REQUEST_ID = "traceId";

    private static OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS)
            .sslSocketFactory(SSLSocketUtils.getSSLSocketFactory())
            .protocols(SSLSocketUtils.getProtocols())
            .build();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");



    /**
     * Get data from URL address (add header)
     * @param url
     * @param headerMap
     * @return Result
     * @throws IOException
     */
    /**
     * GET 增加请求头参数(https)
     * @param url
     * @param headerMap
     * @return Result
     * @throws IOException
     */
    public static String doGetHttpsRequest(String url,Map<String,String> headerMap){
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if(headerMap!=null && headerMap.size()>0){
            Iterator<String> iterator = headerMap.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                String value = headerMap.get(key);
                if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)){
                    requestBuilder.addHeader(key, value);
                }
            }
        }
        Request request = requestBuilder.addHeader(MDC_REQUEST_ID,MDC.get(MDC_REQUEST_ID) ==null? UUID.randomUUID().toString().replace("-", ""):MDC.get(MDC_REQUEST_ID)).build();
        return getResponseMessage(request);
    }

    /**
     * 增加请求头 POST
     * @param url
     * @return
     */
    public static String doPostHttpsRequest(String url, String json, Map<String,String> headerMap) {
        Request.Builder requestBuilder = new Request.Builder().url(url).post(RequestBody.create(json,JSON));
        if(headerMap!=null && headerMap.size()>0){
            Iterator<String> iterator = headerMap.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                String value = headerMap.get(key);
                if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)){
                    requestBuilder.addHeader(key,value);
                }
            }
        }
        Request request = requestBuilder.addHeader(MDC_REQUEST_ID, MDC.get(MDC_REQUEST_ID) ==null? UUID.randomUUID().toString().replace("-", ""):MDC.get(MDC_REQUEST_ID)).build();
        return  getResponseMessage(request);
    }

    private static String getResponseMessage(Request request){
        Response response;
        String body="";
        try {
            response=client.newCall(request).execute();
            body = response.body().string();
        } catch (IOException e) {
            log.error("aaa");
        }
        return body;
    }

//    //异步发送
//    public static void doPostHttpsRequestByJsonStringAsyn(String url, String json) {
//
//        String traceId = MDC.get(MDC_REQUEST_ID);
//
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request = new Request.Builder().url(url).post(body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                MDC.put(MDC_REQUEST_ID, StringUtils.isNotBlank(traceId) ? traceId : UUID.randomUUID().toString().replace("-", ""));
//                log.error("异步发送通知失败,url={},param={},异常:{}", url, json, e);
//                MDC.clear();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                MDC.put(MDC_REQUEST_ID, StringUtils.isNotBlank(traceId) ? traceId : UUID.randomUUID().toString().replace("-", ""));
//                log.info("异步发送通知请求url={},param={}",url, json);
//                log.info("异步发送通知响应:{}",response);
//                if(response.isSuccessful()) {
//                    log.info("异步发送通知响应体信息:{}",response.body().string());
//                }else {
//                    throw new IOException("Unexpected code " + response);
//                }
//                MDC.clear();
//            }
//        });
//    }
}
