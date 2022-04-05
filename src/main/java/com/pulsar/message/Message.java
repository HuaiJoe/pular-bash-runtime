package com.pulsar.message;

import java.util.function.Function;

/**
 * The class Message is a business message container.
 * When send the message instance to pulsar ,I came across the same problem,like ISSUE-3115 https://github.com/streamnative/pulsar/issues/197.
 * So,I labeled deprecated.
 * You can get more details from docs/question.md.
 */
@Deprecated
public class Message<T> {
    String topic;
    T body; // message data

    public Message(String topic, T body) {
        this.topic = topic;
        this.body = body;
    }

    //do some action on message body
    public void map(Function<T, T> fn) {
        body = fn.apply(body);
    }
}
