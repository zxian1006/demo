package com.zx.springboot.demo.thread;

import org.springframework.stereotype.Component;

@Component
public class TestRunnable implements AsyncRunnable {
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("run!!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void error(Exception e) {

    }
}
