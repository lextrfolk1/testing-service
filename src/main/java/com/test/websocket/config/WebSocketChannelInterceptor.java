package com.test.websocket.config;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * WebSocketChannelInterceptor is a Spring STOMP ChannelInterceptor that
 * intercepts inbound WebSocket messages for lifecycle events like CONNECT and DISCONNECT.
 *
 * <p>This interceptor can be extended to implement security, logging, analytics,
 * or message validation logic for WebSocket connections.
 *
 * @author You
 */
@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);

    /**
     * Intercepts messages sent to the inbound message channel before they are processed.
     *
     * @param message the incoming message
     * @param channel the channel the message was received on
     * @return the original or modified message, or {@code null} to block the message
     */
    @Override
    public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        try {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
            StompCommand command = accessor.getCommand();

            if (command == null) {
                return message; // Non-STOMP or malformed message
            }

            switch (command) {
                case CONNECT:
                    logger.info("WebSocket CONNECT - Session ID: {}", accessor.getSessionId());
                    break;
                case DISCONNECT:
                    logger.info("WebSocket DISCONNECT - Session ID: {}", accessor.getSessionId());
                    break;
                default:
                    // You can also inspect SUBSCRIBE, SEND, etc.
                    break;
            }
        } catch (Exception e) {
            logger.error("Error processing WebSocket message: {}", e.getMessage());
        }

        return message;
    }
}
