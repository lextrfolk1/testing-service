package com.test;

import com.test.websocket.publisher.WebSocketNotify;
import org.springframework.stereotype.Service;

@Service
public class TestService {


    @WebSocketNotify(topic = "/topic/messages")
    public String sayHelloo() {
        return "<html><head><style>" +
                "body { background: #f0f4f8; margin: 0; height: 100vh; display: flex; align-items: center; justify-content: center; }" +
                ".card { background: #fff; padding: 40px 60px; border-radius: 16px; box-shadow: 0 4px 24px rgba(0,0,0,0.10); }" +
                ".card h2 { color: #27ae60; margin: 0; font-family: Arial, sans-serif; }" +
                "</style></head><body>" +
                "<div class='card'><h2> Hello, World! </h2></div>" +
                "</body></html>";
    }

}
