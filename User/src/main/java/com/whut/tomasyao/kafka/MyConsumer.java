package com.whut.tomasyao.kafka;

import com.whut.tomasyao.file.util.FileUtil;
import com.whut.tomasyao.kafka.util.KafkaConsumerUtil;
import com.whut.tomasyao.log.service.ILogUserService;
import com.whut.tomasyao.message.mapper.LogMessageMapper;
import com.whut.tomasyao.message.service.ILogMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-03-26 11:03
 */

public class MyConsumer{
    private static final Logger logger = Logger.getLogger(MyConsumer.class);
    private void initConsumer(){
        new Thread(new ConsumerMessage()).start();
    }

    @Autowired
    private ILogUserService userService;
    @Autowired
    private ILogMessageService messageService;

    private class ConsumerMessage implements Runnable{

        @Override
        public void run() {
            KafkaConsumer<String, String> consumer = KafkaConsumerUtil.getInstance();

            consumer.seekToBeginning(new ArrayList<>());

            Map<String, List<PartitionInfo>> topics = consumer.listTopics();
            for(String s : topics.keySet()){
                logger.info("topic: "+s);
            }

            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for(ConsumerRecord<String, String> record : records) {
                    logger.info("fetched from partition: " + record.partition() + ", offset: " + record.offset() +
                            ", key: " + record.key() + ", value: " + record.value());
                    //测试
                    if(record.key().equals("testBlock")){
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        logger.info("testBlock: "+record.value());
                    }
                    //删除文件
                    if(record.key().equals("deleteFile")){
                        try {
                            FileUtil.delete(record.value());//删除文件
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //写入用户操作日志表
                    if(record.key().equals("logUser")){
                        try {
                            userService.addLogUser(record.value());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //写入短信日志表
                    if(record.key().equals("sendMessage")){
                        try {
                            messageService.addOneLogMessage(record.value());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}