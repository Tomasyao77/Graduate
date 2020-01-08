package com.whut.tomasyao.kafka.util;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class KafkaConsumerUtil {
    private static KafkaConsumer kafkaConsumer;
//    static{
//        ResourceBundle bundle = ResourceBundle.getBundle("kafka");
//        String[] topics = bundle.getString("topic").split(",");//可以有多个主题
//        Properties props = new Properties();
//        props.put("bootstrap.servers", bundle.getString("bootstrap.servers"));
//        props.put("enable.auto.commit", bundle.getString("enable.auto.commit"));
//        props.put("group.id", bundle.getString("group.id"));
//        props.put("key.deserializer", bundle.getString("deserializer"));
//        props.put("value.deserializer", bundle.getString("deserializer"));
//        kafkaConsumer = new KafkaConsumer(props);
//        kafkaConsumer.subscribe(Arrays.asList(topics));//消费者订阅话题
//    }
    public static KafkaConsumer getInstance(){
        return kafkaConsumer;
    }
}
