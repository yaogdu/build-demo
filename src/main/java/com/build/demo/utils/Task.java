package com.build.demo.utils;

import java.util.Random;
import java.util.stream.IntStream;

public class Task {
    private static final int NUM_COUNT = 10000;

    public int execute() {
        Random random = new Random();
        return IntStream.range(0, NUM_COUNT)
                .map(i -> random.nextInt())
                .filter(n -> n % 2 == 0)
                .sum();
    }
}