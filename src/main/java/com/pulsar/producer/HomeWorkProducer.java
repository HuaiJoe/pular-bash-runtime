package com.pulsar.producer;

import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The HomeWorkProducer is adapter for pulsar producer.
 */
public class HomeWorkProducer<T> implements Producer<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeWorkProducer.class);
    org.apache.pulsar.client.api.Producer<T> producer;

    /**
     * Receive msg and handle it with using fn
     *
     * @param message the business message,it can contain more business information or request content.
     *                Also, we suggest you define your own message
     */
    @Override
    public void send(T message) {
        try {
            producer.send(message);
        } catch (PulsarClientException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public HomeWorkProducer<T> withProducer(org.apache.pulsar.client.api.Producer<T> producer) {
        this.producer = producer;
        return this;
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
