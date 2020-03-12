package com.zx.springboot.demo.thread;

import java.util.concurrent.*;

public class AsyncThreadPoolExecutor {

    private ThreadPoolExecutor executor;

    private int retry = 0;

    private int coreSize;

    private int maximunSize;

    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public AsyncThreadPoolExecutor() {
        this.coreSize = Runtime.getRuntime().availableProcessors();
        maximunSize = coreSize;
        createPool();

    }

    public AsyncThreadPoolExecutor(int coreSize, int retry) {
        this.coreSize = coreSize;
        this.retry = retry;
        createPool();
    }

    public AsyncThreadPoolExecutor(int coreSize, int maximunSize, int retry) {
        this.coreSize = coreSize;
        this.maximunSize = maximunSize;
        this.retry = retry;
        createPool();
    }

    private void createPool() {
        if( this.coreSize > this.maximunSize){
            this.maximunSize = this.coreSize;
        }

        executor = new ThreadPoolExecutor(this.coreSize, this.maximunSize, 1, TimeUnit.SECONDS, this.queue);
    }

    public void execute(final AsyncRunnable runnable, final int retryCount){
        executor.execute( () -> executeImpl(runnable, retryCount));
    }

    private void executeImpl(AsyncRunnable runnable, int retryCount) {
        try {
            runnable.run();
        }catch (Exception e){
            if( retryCount < retry){
                retryCount += 1;
                executeImpl(runnable, retryCount);
            }else{
                runnable.error(e);
            }
        }
    }

    private <T> T executeImpl(final AsyncCallable<T> callable, int retryCount) {
        try {
            return callable.call();
        }catch (Exception e){
            if( retryCount < retry){
                retryCount += 1;
                executeImpl(callable, retryCount);
            }else{
                callable.error(e);
            }
        }
        return null;
    }
}
