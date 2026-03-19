package com.ynov.coworking.memberservice.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
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

  public static final String GROUP_ID = "member-service-group";

  @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
  private String bootstrapServers;

  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    Map<String, Object> c = new HashMap<>();
    c.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    JsonSerializer<Object> valueSerializer = new JsonSerializer<>();
    valueSerializer.setAddTypeInfo(false);
    return new DefaultKafkaProducerFactory<>(c, new StringSerializer(), valueSerializer);
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(
      ProducerFactory<String, Object> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public ConsumerFactory<String, MemberEvent> memberEventConsumerFactory() {
    JsonDeserializer<MemberEvent> des = new JsonDeserializer<>(MemberEvent.class);
    des.addTrustedPackages("com.ynov.coworking.memberservice.kafka");
    Map<String, Object> p = new HashMap<>();
    p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
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
    f.setConsumerFactory(memberEventConsumerFactory);
    return f;
  }
}
