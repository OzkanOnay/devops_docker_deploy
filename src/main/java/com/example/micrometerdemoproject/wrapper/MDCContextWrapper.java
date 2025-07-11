package com.example.micrometerdemoproject.wrapper;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class MDCContextWrapper {
    public static <T> Callable<T> wrapWithMdcContext(Callable<T> task) {
        //save the current MDC context
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            setMDCContext(contextMap);
            try {
                return task.call();
            } finally {
                // once the task is complete, clear MDC
                MDC.clear();
            }
        };
    }

    public static Runnable wrapWithMdcContext(Runnable task) {
        //save the current MDC context
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            setMDCContext(contextMap);
            try {
                task.run();
            } finally {
                // once the task is complete, clear MDC
                MDC.clear();
            }
        };
    }

    public static void setMDCContext(Map<String, String> contextMap) {
        MDC.clear();
        if (contextMap != null) {
            MDC.setContextMap(contextMap);
        }
    }

    public static Runnable withMdc(Runnable runnable) {
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(mdc);
            runnable.run();
        };
    }

    public static <U> Supplier<U> withMdc(Supplier<U> supplier) {
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        return (Supplier) () -> {
            MDC.setContextMap(mdc);
            return supplier.get();
        };
    }
}
