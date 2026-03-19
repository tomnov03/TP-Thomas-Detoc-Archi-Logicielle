package com.ynov.coworking.memberservice.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaConfig {

  public static final String BOOTSTRAP_SERVERS = "localhost:9092";
  public static final String GROUP_ID = "member-service-group";

  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    Map<String, Object> c = new HashMap<>();
    c.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    c.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    c.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(c);
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(
      ProducerFactory<String, Object> producerFactory) {
    return new KafkaTemplate<>(Objects.requireNonNull(producerFactory, "producerFactory"));
  }

  @Bean
  public ConsumerFactory<String, MemberEvent> memberEventConsumerFactory() {
    JsonDeserializer<MemberEvent> des = new JsonDeserializer<>(MemberEvent.class);
    des.addTrustedPackages("*");
    Map<String, Object> p = new HashMap<>();
    p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    p.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(p, new StringDeserializer(), des);
  }

  @Bean(name = "memberKafkaListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, MemberEvent>
      memberKafkaListenerContainerFactory(
          ConsumerFactory<String, MemberEvent> memberEventConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, MemberEvent> f =
        new ConcurrentKafkaListenerContainerFactory<>();
    f.setConsumerFactory(
        Objects.requireNonNull(memberEventConsumerFactory, "memberEventConsumerFactory"));
    return f;
  }
}
