package com.zx.springboot.demo.controller;

import com.zx.springboot.demo.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadContrller {
    @Autowired
    ThreadService threadService;

    @RequestMapping("/testThread")
    public int test(){
        System.out.println("start");
        for (int i = 0; i < 10; i++) {
            System.out.println("-----i" + i);
            threadService.test(i);
        }
        System.out.println("end");
        return 0;
    }

}
