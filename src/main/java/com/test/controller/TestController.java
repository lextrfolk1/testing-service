package com.test.controller;


import com.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping
    public String sayHello() {
        return testService.sayHelloo();
    }


    @GetMapping("/health")
    public String healthCheck() {
        return "<html><body><div style='text-align:center;'><h2 style='color:green;'>Service is up and running!</h2></div></body></html>";
    }


}
