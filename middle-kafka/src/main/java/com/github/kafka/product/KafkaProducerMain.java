package com.github.kafka.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

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

    private KafkaProducer init(){
        kafkaProps.put("bootstrap.servers", "47.105.137.27:9092");
        kafkaProps.put("acks", "all");
        kafkaProps.put("retries", 0);
        kafkaProps.put("batch.size", 16384);
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<String, String>(kafkaProps);
    }


    public static void main(String[] args) {
        KafkaProducerMain kafkaProducerMain = new KafkaProducerMain();
        KafkaProducer producer = kafkaProducerMain.init();
        kafkaProducerMain.sendSync(producer);
        kafkaProducerMain.sendAsync(producer);
        producer.close();

    }

    void sendAsync(KafkaProducer kafkaProducer){
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("CustomerCountry", "Precision Products", "USA");
        kafkaProducer.send(record, new KafkaProducerCallback());
    }

    void sendSync(KafkaProducer producer){
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("CustomerCountry", "Precision Products", "France");
        try {
            producer.send(record).get();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
