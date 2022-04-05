package com.pulsar.functions;

import java.util.function.Function;

import static java.lang.String.format;

/**
 * The classic Exclamation appends an exclamation at the end of the input.
 */
public class Exclamation implements Function<String, String> {
    @Override
    public String apply(String input) {
        return format("%s!", input);
    }
}
