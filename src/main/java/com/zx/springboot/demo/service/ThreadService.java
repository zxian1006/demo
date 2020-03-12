package com.zx.springboot.demo.service;

import com.zx.springboot.demo.thread.AsyncThreadPoolExecutor;
import com.zx.springboot.demo.thread.TestRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

    @Autowired
    TestRunnable testRunnable;

    AsyncThreadPoolExecutor poolExecutor = new AsyncThreadPoolExecutor();


    @Async
    public void test(int i){
        System.out.println("i=====" + i);
        poolExecutor.execute(testRunnable, 0);
    }
}
