package org.example;

import java.util.function.Function;

public class FibonacciHandler implements Function<Integer, String> {
    private Long fibonacci(Integer i) {
        if (i <= 2) {
            return 1L;
        }
        return fibonacci(i - 1) + fibonacci(i - 2);
    }

    @Override
    public String apply(Integer i) {
        return String.valueOf(i);
    }
}
