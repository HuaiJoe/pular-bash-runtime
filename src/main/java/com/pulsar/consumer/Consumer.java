package com.pulsar.consumer;

import java.io.Closeable;

/**
 * The interface Consumer describe the consumer business service.It is different from pulsar consumer.
 * For business consumer are not care  about pass-service anymore.We can use pulsar or kafka instead each other.
 */
public interface Consumer<T> extends Closeable {
    /**
     * Receive msg and handle it with using fn
     *
     * @param fns the business handle function
     */
    void receive(java.util.function.Consumer<T> ...fns);
}
