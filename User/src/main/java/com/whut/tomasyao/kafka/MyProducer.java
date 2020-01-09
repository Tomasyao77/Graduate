package com.whut.tomasyao.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.*;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-26 11:03
 */

public class MyProducer{
    private static final Logger logger = Logger.getLogger(MyProducer.class);
    private void initProducer(){
        new Thread(new ProducerMessage()).start();
    }

    private class ProducerMessage implements Runnable{

        @Override
        public void run() {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("kafka");
                Properties props = new Properties();
                props.put("bootstrap.servers", bundle.getString("bootstrap.servers"));
                props.put("acks", "0");
                props.put("retries", 0);
                props.put("batch.size", 16384);
                props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

                Producer<String, String> producer = new KafkaProducer<String, String>(props);
                int i = 0;
                while(true) {
                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("controller", "AgeEstimationController");
                    jsonObject1.put("method", "POST");
                    jsonObject1.put("service", "updateUser");
                    jsonObject1.put("date", ""+(new Date().getTime()));
                    jsonObject.put("userId", 1);
                    jsonObject.put("action", jsonObject1.toString());
                    ProducerRecord<String, String> record = new ProducerRecord<>("logUser", "logUser", jsonObject.toString());
                    producer.send(record, new Callback() {
                        public void onCompletion(RecordMetadata metadata, Exception e) {
                            if (e != null)
                                e.printStackTrace();
                            logger.info("message send to partition: " + metadata.partition() + ", offset: " + metadata.offset());
                        }
                    });
                    i++;
                    Thread.sleep(10000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}