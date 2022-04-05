package com.pulsar.consumer;

import org.apache.pulsar.client.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The HomeWorkConsumer is adapter for pulsar consumer.
 */
public class HomeWorkConsumer<T> implements Consumer<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger(HomeWorkConsumer.class);
    org.apache.pulsar.client.api.Consumer<T> consumer;

    public HomeWorkConsumer<T> withConsumer(org.apache.pulsar.client.api.Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * Receive msg and handle it with using fn
     *
     * @param fns the business handle function
     */
    @Override
    public void receive(java.util.function.Consumer<T>... fns) {
        Message<T> msg = null;
        try {
            msg = consumer.receive();
            LOGGER.debug("received message: " + new String(msg.getData()));
            for (java.util.function.Consumer<T> fn : fns) {
                fn.accept(msg.getValue());
            }
            consumer.acknowledge(msg);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            // Message failed to process, redeliver later
            consumer.negativeAcknowledge(msg);
        }
    }

    @Override
    public void close() throws IOException {
        consumer.close();
    }
}
