package com.pulsar;

import com.pulsar.consumer.Consumer;
import com.pulsar.consumer.HomeWorkConsumer;
import com.pulsar.functions.Exclamation;
import com.pulsar.message.StringMsg;
import com.pulsar.producer.HomeWorkProducer;
import com.pulsar.producer.Producer;
import org.apache.pulsar.client.api.BatchReceivePolicy;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The HomeWorkApp transfer message from pulsar topic to another topic.
 */
public class HomeWorkApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeWorkApp.class);

    public static void main(String[] args) throws IOException {
        // verify input parameters
        if (args.length != 4) {
            LOGGER.error("pulsar service url, from topic, to topic and subscription name parameters are needed.");
            return;
        }
        String serviceUrl = args[0];
        String fromTopic = args[1];
        String toTopic = args[2];
        String subscriptionName = args[3];
        LOGGER.debug(String.format("serviceUrl: %s, fromTopic: %s, toTopic: %s, subscriptionName: %s", serviceUrl, fromTopic, toTopic, subscriptionName));

        // build pulsar client. We will inject current client to pulsar producer and pulsar consumer later.
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(serviceUrl)
                .build();

        // build business consumer and business producer
        Producer<StringMsg> producer = buildProducer(client, toTopic);
        Consumer<StringMsg> consumer = buildConsumer(client, fromTopic, subscriptionName);

        // start consumer at fix period.
        // If you use these codes for production environment,pool size parameter and any other thread parameter should be given.
        // You can add more consume function here
        Exclamation exclamation = new Exclamation();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(
                () ->consumer.receive(msg -> {LOGGER.info("exclamation function: "+exclamation.apply(msg.getBody()));},producer::send),
                1, 1, TimeUnit.SECONDS);

        // todo: close pulsar resource
        // producer.close();
        // consumer.close();
        // client.close();
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


    static Consumer<StringMsg> buildConsumer(PulsarClient client, String topic, String subscriptionName) {
        org.apache.pulsar.client.api.Consumer<StringMsg> pulsarConsumer = null;
        try {
            pulsarConsumer = client.newConsumer(JSONSchema.of(StringMsg.class))
                    .topic(topic)
                    .subscriptionName(subscriptionName)
                    .batchReceivePolicy(BatchReceivePolicy.builder()
                            .maxNumMessages(100)
                            .maxNumBytes(1024 * 1024)
                            .timeout(200, TimeUnit.MILLISECONDS)
                            .build())
                    .subscribe();
        } catch (PulsarClientException e) {
            LOGGER.error("build consumer failed");
        }
        return new HomeWorkConsumer<StringMsg>().withConsumer(pulsarConsumer);
    }
}
