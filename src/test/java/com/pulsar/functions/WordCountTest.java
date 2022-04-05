package com.pulsar.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCountTest {

    @Test
    void should_return_1_when_input_one_word() {
        WordCounter counter = new WordCounter();
        assertEquals(1, counter.apply("hello"));
    }

    @Test
    void should_return_0_when_input_is_blank() {
        WordCounter counter = new WordCounter();
        assertEquals(0, counter.apply(""));
    }

    @Test
    void should_return_0_when_input_only_spaces() {
        WordCounter counter = new WordCounter();
        assertEquals(0, counter.apply("    "));
    }

    @Test
    void should_return_1_when_input_word_has_many_prefix_spaces() {
        WordCounter counter = new WordCounter();
        assertEquals(1, counter.apply("  hello"));
    }

    @Test
    void should_return_1_when_input_word_has_many_postfix_spaces() {
        WordCounter counter = new WordCounter();
        assertEquals(1, counter.apply("hello  "));
    }

    @Test
    void should_return_2_when_input_two_words() {
        WordCounter counter = new WordCounter();
        assertEquals(2, counter.apply("hello  world"));
    }
}