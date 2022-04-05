package com.pulsar;

import com.pulsar.message.StringMsg;
import com.pulsar.producer.HomeWorkProducer;
import com.pulsar.producer.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * class ProducerAppDemo send StringMsg to Pulsar
 */
public class ProducerAppDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAppDemo.class);

    public static void main(String[] args) throws IOException {
        // verify input parameters
        if (args.length != 3) {
            LOGGER.error("pulsar service url, topic and message parameters are needed.");
            return;
        }
        String serviceUrl = args[0];
        String topic = args[1];
        String message = args[2];

        LOGGER.debug(String.format("serviceUrl: %s, topic: %s, message: %s", serviceUrl, topic, message));

        // build pulsar client. We will inject current client to pulsar producer and pulsar consumer later.
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(serviceUrl)
                .build();

        // build business producer
        Producer<StringMsg> producer = buildProducer(client, topic);
        producer.send(new StringMsg("test", message));

        // close pulsar resource
        producer.close();
        client.close();
    }

    static Producer<StringMsg> buildProducer(PulsarClient client, String topic) {
        org.apache.pulsar.client.api.Producer<StringMsg> pulsarProducer = null;
        try {
            pulsarProducer = client.newProducer(JSONSchema.of(StringMsg.class))
                    .topic(topic)
                    .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                    .sendTimeout(10, TimeUnit.SECONDS)
                    .blockIfQueueFull(true)
                    .create();
        } catch (PulsarClientException e) {
            LOGGER.error("build producer failed");
        }
        return new HomeWorkProducer<StringMsg>().withProducer(pulsarProducer);
    }

}
