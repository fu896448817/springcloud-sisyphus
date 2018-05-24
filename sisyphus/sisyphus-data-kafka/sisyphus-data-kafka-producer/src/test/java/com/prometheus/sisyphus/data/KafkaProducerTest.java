package com.prometheus.sisyphus.data;

import com.prometheus.sisyphus.data.kafka.producer.KafkaProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for simple App.
 *
 * Created by ${USER} on ${DATE}.
 */
public class KafkaProducerTest{

    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    public void producer() {
        kafkaProducer.send("hello", "aaaaa");
    }

}