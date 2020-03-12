package com.zx.springboot.demo.thread;

import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface AsyncCallable<T> {

    T call() throws Exception;

    default void error(Exception e){
        LoggerFactory.getLogger(AsyncCallable.class).error("error happen in async callable", e);
    }
}
