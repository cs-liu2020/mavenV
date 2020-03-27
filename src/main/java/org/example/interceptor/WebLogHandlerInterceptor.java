package org.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * description: interceptor <br>
 * date: 2019/11/8 10:43 <br>
 * author:  zhibei.liu@opay-inc.com<br>
 */
@Slf4j
@Component
public class WebLogHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String MDC_REQUEST_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ctxTraceId = null;

        // 判断Http header中是否有traceId字段,如果没有,则通过随机数生成
        if (StringUtils.isNotBlank(request.getHeader(MDC_REQUEST_ID))) {
            ctxTraceId = request.getHeader(MDC_REQUEST_ID);
        } else {
            ctxTraceId = getTraceId();
        }
        MDC.put(MDC_REQUEST_ID, ctxTraceId);
        log.info("请求的traceId:{}", MDC.get(MDC_REQUEST_ID));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }

    // 通过随机数生成traceId,也可以通过其他方式实现,只要保证唯一即可
    private static String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
