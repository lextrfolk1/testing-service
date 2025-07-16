package com.test.websocket.publisher.impl;


import com.test.websocket.publisher.MessagePublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class InMemoryMessagePublisher implements MessagePublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public InMemoryMessagePublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void publish(String destination, Object message) {
        messagingTemplate.convertAndSend(destination, message);
    }
}
