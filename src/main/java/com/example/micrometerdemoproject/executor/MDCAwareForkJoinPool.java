package com.example.micrometerdemoproject.executor;

import com.example.micrometerdemoproject.wrapper.MDCContextWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static com.example.micrometerdemoproject.wrapper.MDCContextWrapper.wrapWithMdcContext;

public class MDCAwareForkJoinPool extends ForkJoinPool {
    //Override constructors which you need

    @Override
    public <T> ForkJoinTask<T> submit(Callable<T> task) {
        return super.submit(MDCContextWrapper.wrapWithMdcContext(task));
    }

    @Override
    public <T> ForkJoinTask<T> submit(Runnable task, T result) {
        return super.submit(wrapWithMdcContext(task), result);
    }

    @Override
    public ForkJoinTask<?> submit(Runnable task) {
        return super.submit(wrapWithMdcContext(task));
    }

    @Override
    public void execute(Runnable task) {
        super.execute(wrapWithMdcContext(task));
    }
}
