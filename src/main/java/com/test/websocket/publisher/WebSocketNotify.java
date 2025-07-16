package com.test.websocket.publisher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to notify WebSocket clients.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebSocketNotify {
    /**
     * WebSocket topic to send data.
     *
     * @return the WebSocket topic
     */
    String topic();

    /**
     * Type of request to distinguish.
     *
     * @return the request type
     */
    String requestType() default "";
}
