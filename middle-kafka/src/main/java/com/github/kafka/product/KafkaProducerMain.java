package com.github.kafka.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collections;
import java.util.Properties;

/**
 * <p>Main使用kafka-clients连接kafka</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@Slf4j
public class KafkaProducerMain {

    private static Properties kafkaProps = new Properties();

    private KafkaProducer<String, String> init(){
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("acks", "all");
        kafkaProps.put("retries", 0);
        kafkaProps.put("batch.size", 16384);
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("auto.create.topics.enable", true);
        AdminClient create = KafkaAdminClient.create(kafkaProps);
        create.createTopics(Collections.singleton(new NewTopic("CustomerCountry", 1, (short) 1)));
        create.close();
        return new KafkaProducer<>(kafkaProps);
    }


    public static void main(String[] args) {
        KafkaProducerMain kafkaProducerMain = new KafkaProducerMain();
        KafkaProducer<String, String> producer = kafkaProducerMain.init();
        kafkaProducerMain.sendSync(producer);
        kafkaProducerMain.sendAsync(producer);
        producer.close();
    }

    void sendAsync(KafkaProducer<String, String> kafkaProducer){
        ProducerRecord<String, String> record = new ProducerRecord<>("CustomerCountry", "Precision Products", "USA");
        kafkaProducer.send(record, new KafkaProducerCallback());
    }

    void sendSync(KafkaProducer<String,String> producer){
        ProducerRecord<String, String> record = new ProducerRecord<>("CustomerCountry", "Precision Products", "France");
        try {
            producer.send(record).get();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
