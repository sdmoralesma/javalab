package com.smorales.javalab.business.files.async;

import javax.ejb.AsyncResult;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class FutureDelegator implements Future<Object> {

    private final Future<?> future;

    FutureDelegator(Future<?> future) {
        this.future = future;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        AsyncResult<?> asyncResult = (AsyncResult<?>) future.get();
        return asyncResult == null ? null : asyncResult.get();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        AsyncResult<?> asyncResult = (AsyncResult<?>) future.get(timeout, unit);
        return asyncResult == null ? null : asyncResult.get();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }
}