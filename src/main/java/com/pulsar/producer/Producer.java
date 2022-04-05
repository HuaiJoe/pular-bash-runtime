package com.pulsar.producer;

import java.io.Closeable;

/**
 * The interface Producer describe the producer business service.It is different from pulsar producer.
 * For business producer are not care  about pass-service anymore.We can use pulsar or kafka instead each other.
 */
public interface Producer<T> extends Closeable {
    /**
     * Receive msg and handle it with using fn
     *
     * @param message the business message ,not pulsar message
     */
    void send(T message);
}
