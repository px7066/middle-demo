package com.github.kafka.consumer;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
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
        kafkaProps.put("acks", "all");
        kafkaProps.put("retries", 0);
        kafkaProps.put("batch.size", 16384);
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
        kafkaConsumer.subscribe(Collections.singleton("CustomerCountry"));
        kafkaConsumerMain.consume(kafkaConsumer);
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




}
