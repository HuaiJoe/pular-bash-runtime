package com.pulsar.producer;

import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HomeWorkProducerTest {
    @Mock
    private org.apache.pulsar.client.api.Producer<String> pulsarProducer;

    private HomeWorkProducer<String> producer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        producer = new HomeWorkProducer<String>().withProducer(pulsarProducer);
    }

    @Test
    void send() throws PulsarClientException {
        //when
        producer.send(anyString());

        //then
        verify(pulsarProducer, times(1)).send(anyString());
    }

}