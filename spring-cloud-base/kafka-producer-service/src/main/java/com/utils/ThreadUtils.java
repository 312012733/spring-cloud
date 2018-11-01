package com.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils
{
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(50);
    
    public static void execute(Runnable task)
    {
        executor.submit(task);
    }
}
