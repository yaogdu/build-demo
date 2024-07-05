package com.build.demo.utils;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ResultWriter {
    public static void writeResultsToFile(String filename, List<Future<Integer>> results) throws IOException, ExecutionException, InterruptedException {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (Future<Integer> result : results) {
                if (result.isDone() && !result.isCancelled()) {
                    writer.write(result.get() + "\n");
                }
            }
        }
    }
}