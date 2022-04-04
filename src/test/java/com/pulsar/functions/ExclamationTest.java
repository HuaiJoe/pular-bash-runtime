package com.pulsar.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExclamationTest {
    @Test
    void should_append_exclamation_when_given_non_blank_word() {
        Exclamation exclamationFunc = new Exclamation();
        assertEquals("hello!",exclamationFunc.apply("hello"));
    }
}