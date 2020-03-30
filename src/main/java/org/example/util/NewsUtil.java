package org.example.util;

import lombok.extern.log4j.Log4j2;
import org.example.constant.WechatUrlConstant;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 上传图片
 */
@Log4j2
public class NewsUtil {

    private static  RestTemplate restTemplate = new RestTemplate();

    public static String uploadimg(String filePath) {

        String accessToken = AccessTokenUtil.getAccessToken().getToken();
        if (accessToken != null) {
            String url = WechatUrlConstant.UPLOADING_PICTURE+"?access_token="+accessToken;
            log.info("UPLOAD_IMG_URL:{}",url);

            //设置请求体，注意是LinkedMultiValueMap
            MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
            //设置上传文件
            FileSystemResource fileSystemResource = new FileSystemResource(filePath);
            data.add("media", fileSystemResource);

            //上传文件,设置请求头
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            httpHeaders.setContentLength(fileSystemResource.getFile().length());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(data,
                    httpHeaders);
            try{

                String resultJSON = restTemplate.postForObject(url, requestEntity, String.class);
                log.info("上传返回的信息是：{}",resultJSON);
                return resultJSON;
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return null;

    }
}
