package com.github.kafka.consumer;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * <p>消费者main方法</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class KafkaConsumerMain {


    private static Properties kafkaProps = new Properties();

    KafkaConsumer<String, String> init(){
        kafkaProps.put("bootstrap.servers", "47.105.137.27:9092");
        kafkaProps.put("group.id", "CountryCounter");
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("auto.commit.offset", false);
        kafkaProps.put("enable.auto.commit", false);
        return new KafkaConsumer<>(kafkaProps);
    }

    public static void main(String[] args) {
        KafkaConsumerMain kafkaConsumerMain = new KafkaConsumerMain();
        KafkaConsumer kafkaConsumer = kafkaConsumerMain.init();
//        kafkaConsumer.subscribe(Collections.singleton("CustomerCountry"), new HandleRebalance());
//        kafkaConsumerMain.consume(kafkaConsumer);
        kafkaConsumerMain.consumeByPartition(kafkaConsumer);
    }

    void consume(KafkaConsumer kafkaConsumer) {
        try {
            while (true){
                ConsumerRecords<String, String> record = kafkaConsumer.poll(Duration.ofMinutes(100L));
                for (ConsumerRecord<String, String> stringStringConsumerRecord : record) {
                    System.out.printf("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n", stringStringConsumerRecord.topic(),
                            stringStringConsumerRecord.partition(), stringStringConsumerRecord.offset(), stringStringConsumerRecord.key(),
                            stringStringConsumerRecord.value());
                }
                try {
                    kafkaConsumer.commitSync();
                } catch (CommitFailedException e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }
    }

    void consumeByPartition(KafkaConsumer kafkaConsumer){
        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor("CustomerCountry");
        List<TopicPartition> partitions = new ArrayList<>();
        if(null != partitionInfos){
            for (PartitionInfo partitionInfo : partitionInfos) {
                partitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            }
            kafkaConsumer.assign(partitions);
            while (true){
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMinutes(1000L));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n", record.topic(),
                            record.partition(), record.offset(), record.key(),
                            record.value());
                }
                kafkaConsumer.commitSync();
            }
        }
    }




}
