package com.zx.springboot.demo.thread;

import org.springframework.stereotype.Component;

@Component
public interface AsyncRunnable {

    void run();

    void error(Exception e);
}
