package com.test.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class WebSocketTaskSchedulerConfig {

    public static final int POOL_SIZE = 2;
    public static final String THREAD_NAME_PREFIX = "ws-heartbeat-";

    /**
     * Creates a ThreadPoolTaskScheduler bean for scheduling tasks.
     * This scheduler is used for sending heartbeat pings to clients.
     *
     * @return a configured ThreadPoolTaskScheduler instance
     */
    @Bean(name = "webSocketTaskScheduler")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.setThreadNamePrefix(THREAD_NAME_PREFIX);
        scheduler.initialize();
        return scheduler;
    }
}
