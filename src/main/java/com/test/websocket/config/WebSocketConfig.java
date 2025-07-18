package com.test.websocket.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configures WebSocket support for the application using STOMP protocol over SockJS.
 * Registers endpoints, heartbeat intervals, message broker prefixes, and interceptors.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    public static final String STOMP_ENDPOINT = "/websocket-endpoint";
    public static final String TOPIC_PREFIX = "/topic";
    public static final String APP_PREFIX = "/app";
    public static final String ALLOWED_ORIGINS = "*";
    /**
     * Heartbeat intervals: [server-to-client, client-to-server] in milliseconds
     */
    public static final long[] HEARTBEAT = {10000, 10000}; // every 10 seconds

    private final WebSocketChannelInterceptor interceptor;
    private final ThreadPoolTaskScheduler taskScheduler;

    /**
     * Constructor injection for WebSocket dependencies.
     *
     * @param interceptor     Custom ChannelInterceptor to handle WebSocket lifecycle events
     * @param taskScheduler TaskScheduler bean used for heartbeat scheduling
     */
    public WebSocketConfig(WebSocketChannelInterceptor interceptor,
                           @Qualifier("webSocketTaskScheduler") ThreadPoolTaskScheduler taskScheduler) {
        this.interceptor = interceptor;
        this.taskScheduler = taskScheduler;
    }

    /**
     * Configure the message broker with topic prefix, application destination prefix,
     * heartbeat intervals, and the task scheduler responsible for them.
     *
     * @param registry the MessageBrokerRegistry to configure
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(TOPIC_PREFIX)
                // Set heartbeat interval for server and client (10s each)
                .setHeartbeatValue(HEARTBEAT)
                // Provide the thread scheduler that will send heartbeat pings
                .setTaskScheduler(taskScheduler);

        // All messages from client starting with "/app" will be routed to controller methods
        registry.setApplicationDestinationPrefixes(APP_PREFIX);
    }

    /**
     * Register the STOMP endpoint that clients will use to connect to the WebSocket server.
     * SockJS is enabled to provide fallback options for browsers that don’t support native WebSockets.
     *
     * @param registry the StompEndpointRegistry to register the endpoints on
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint(STOMP_ENDPOINT) // SockJS fallback
                .setAllowedOriginPatterns("*")
                .withSockJS();

    }

    /**
     * Attach a custom channel interceptor to monitor or manipulate messages
     * on the inbound client channel (e.g., connect/disconnect events, authentication).
     *
     * @param registration the ChannelRegistration to which the interceptor is added
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(interceptor);
    }
}
