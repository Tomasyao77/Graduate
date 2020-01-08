package com.whut.tomasyao.kafka.util;

import org.apache.kafka.clients.producer.*;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-26 11:03
 */

public class KafkaProducerUtil {
    private static Logger logger = Logger.getLogger(KafkaProducerUtil.class);
    private static Producer<String, String> producer;

//    static {
//        ResourceBundle bundle = ResourceBundle.getBundle("kafka");
//        Properties props = new Properties();
//        props.put("bootstrap.servers", bundle.getString("bootstrap.servers"));
//        props.put("acks", "0");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("key.serializer", bundle.getString("serializer"));
//        props.put("value.serializer", bundle.getString("serializer"));
//
//        producer = new KafkaProducer<>(props);
//    }

    public static void producer(String topic, String k, String v){

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, k, v);
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e != null)
                    e.printStackTrace();
                logger.info("kfk msg send to partition: " + metadata.partition() + ", offset: " + metadata.offset());
            }
        });
    }

}