package com.pulsar.functions;

import java.util.Arrays;
import java.util.function.Function;

public class WordCounter implements Function<String,Long> {
    @Override
    public Long apply(String input) {
        return Arrays.stream(input.split("\\s+"))
                .filter(s->!s.isEmpty())
                .count();
    }
}
