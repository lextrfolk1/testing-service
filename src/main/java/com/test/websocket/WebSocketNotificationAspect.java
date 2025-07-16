package com.test.websocket;


import com.test.websocket.publisher.MessagePublisher;
import com.test.websocket.publisher.WebSocketNotify;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebSocketNotificationAspect {

    private final MessagePublisher messagePublisher;

    private final Logger logger = LoggerFactory.getLogger(WebSocketNotificationAspect.class);

    public WebSocketNotificationAspect(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Around("@annotation(webSocketNotify)")
    public Object sendWebSocketNotification(ProceedingJoinPoint joinPoint, WebSocketNotify webSocketNotify) throws Throwable {
        Object result;

        try {
            // Proceed with the actual REST method execution
            result = joinPoint.proceed();
            logger.info("Method executed successfully, preparing to send WebSocket notification");
            // Send the result via WebSocket only after successful commit
            messagePublisher.publish(webSocketNotify.topic(), result);
            logger.info("WebSocket notification sent to topic: {}", webSocketNotify.topic());
        } catch (Exception e) {
            logger.error("Error during method execution or WebSocket notification: {}", e.getMessage());
            // If the transaction fails, do not send a WebSocket message
            throw e;
        }

        return result;  // Ensure the original response is still returned to the REST client
    }
}
