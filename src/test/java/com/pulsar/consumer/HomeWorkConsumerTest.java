package com.pulsar.consumer;

import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class HomeWorkConsumerTest {

    @Mock
    private org.apache.pulsar.client.api.Consumer<String> pulsarConsumer;
    @Mock
    private org.apache.pulsar.client.api.Message<String> message;

    private HomeWorkConsumer<String> consumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        consumer = new HomeWorkConsumer<String>().withConsumer(pulsarConsumer);
    }

    @Test
    void should_receive_act_once_when_call_homework_consumer_receive() throws PulsarClientException {
        when(pulsarConsumer.receive()).thenReturn(message);
        consumer.receive(System.out::println);
        verify(pulsarConsumer, times(1)).receive();
    }
}