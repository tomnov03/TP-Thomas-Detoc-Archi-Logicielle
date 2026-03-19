package com.ynov.coworking.reservationservice.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
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

  public static final String GROUP_ROOM = "reservation-service-room";
  public static final String GROUP_MEMBER = "reservation-service-member";

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
  public ConsumerFactory<String, RoomEvent> roomEventConsumerFactory() {
    JsonDeserializer<RoomEvent> des = new JsonDeserializer<>(RoomEvent.class);
    des.addTrustedPackages("com.ynov.coworking.reservationservice.kafka");
    Map<String, Object> p = new HashMap<>();
    p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    p.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ROOM);
    p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(p, new StringDeserializer(), des);
  }

  @Bean(name = "reservationRoomListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, RoomEvent>
      reservationRoomListenerContainerFactory(
          @Qualifier("roomEventConsumerFactory")
              ConsumerFactory<String, RoomEvent> roomEventConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, RoomEvent> f =
        new ConcurrentKafkaListenerContainerFactory<>();
    f.setConsumerFactory(roomEventConsumerFactory);
    return f;
  }

  @Bean
  public ConsumerFactory<String, MemberEvent> memberEventConsumerFactory() {
    JsonDeserializer<MemberEvent> des = new JsonDeserializer<>(MemberEvent.class);
    des.addTrustedPackages("com.ynov.coworking.reservationservice.kafka");
    Map<String, Object> p = new HashMap<>();
    p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    p.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_MEMBER);
    p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(p, new StringDeserializer(), des);
  }

  @Bean(name = "reservationMemberListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, MemberEvent>
      reservationMemberListenerContainerFactory(
          @Qualifier("memberEventConsumerFactory")
              ConsumerFactory<String, MemberEvent> memberEventConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, MemberEvent> f =
        new ConcurrentKafkaListenerContainerFactory<>();
    f.setConsumerFactory(memberEventConsumerFactory);
    return f;
  }
}
