package com.build.demo.controller;

import com.build.demo.utils.ResultWriter;
import com.build.demo.utils.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class TaskController {

    @Resource
    private TaskExecutor myTaskExecutor;
    @GetMapping("/submit-tasks")
    public String submitTasks() throws ExecutionException, InterruptedException, IOException {
        List<Future<Integer>> results = myTaskExecutor.submitTasks(2000);
        // 等待所有任务完成
        while (!myTaskExecutor.getResults().stream().allMatch(Future::isDone)) {
            Thread.sleep(100);
        }
        // 写入结果到文件
        ResultWriter.writeResultsToFile("results.txt", results);
        return "Tasks completed and results written to file!";
    }

    @GetMapping("/cancel-tasks")
    public List<Integer> cancelTasks() throws ExecutionException, InterruptedException {
        myTaskExecutor.cancelTasks();
        // 返回部分完成的结果
        return myTaskExecutor.getPartialResults();
    }

    @GetMapping("/read-results")
    public String readResults() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("results.txt"));
            return String.join("\n", lines);
        } catch (IOException e) {
            return "Error reading results from file: " + e.getMessage();
        }
    }
}