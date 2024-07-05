package com.build.demo.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component("myTaskExecutor")
public class TaskExecutor {
    private ExecutorService executor = Executors.newSingleThreadExecutor();;
    private final List<Future<Integer>> results = new CopyOnWriteArrayList<>();

    public List<Future<Integer>> submitTasks(int taskCount) {
        resetExecutor(); // 重置执行器
        results.clear(); // 清空之前的结果
        for (int i = 0; i < taskCount; i++) {
            Task task = new Task();
            Future<Integer> result = executor.submit(task::execute);
            results.add(result);
        }
        executor.shutdown();
        return results;
    }

    public void cancelTasks() {
        for (Future<Integer> result : results) {
            if (!result.isDone()) {
                result.cancel(true);
            }
        }
        executor.shutdownNow();
    }

    public List<Future<Integer>> getResults() {
        return results;
    }

    public List<Integer> getPartialResults() throws ExecutionException, InterruptedException {
        List<Integer> partialResults = new ArrayList<>();
        for (Future<Integer> result : results) {
            if (result.isDone() && !result.isCancelled()) {
                partialResults.add(result.get());
            }
        }
        return partialResults;
    }


    private void resetExecutor() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        executor = Executors.newSingleThreadExecutor();
    }
}