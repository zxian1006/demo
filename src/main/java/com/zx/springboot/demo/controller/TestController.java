package com.zx.springboot.demo.controller;

import com.zx.test.start.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;


@RestController
public class TestController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/")
    public String index(){
        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        hashMap.remove("");
    //        AbstractQueuedSynchronizer
        String a = "1";
        return helloService.sayHello();
    }

    public static void main(String[] args) {
        int a = 2;
        int b = 3;
        int c = a = b;
        System.out.println(c);
    }
}

