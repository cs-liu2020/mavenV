package org.example.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * description: config <br>
 * date: 2019/11/8 14:35 <br>
 * author:  zhibei.liu@opay-inc.com<br>
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private WebLogHandlerInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }
}