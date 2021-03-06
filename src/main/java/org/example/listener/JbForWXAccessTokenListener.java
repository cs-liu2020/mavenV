package org.example.listener;

import lombok.extern.log4j.Log4j2;
import org.example.util.AccessTokenUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 项目启动初始化信息
 */
@Log4j2
@Component
public class JbForWXAccessTokenListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){

            Runnable runnable = new Runnable() {
                public void run() {
                    /**
                     * 定时设置accessToken
                     */
                    log.info("开始定时调用获取access_token");
                    AccessTokenUtil.initAndSetAccessToken();
                }
            };

            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(runnable, 1, 7000, TimeUnit.SECONDS);
        }

    }
}
