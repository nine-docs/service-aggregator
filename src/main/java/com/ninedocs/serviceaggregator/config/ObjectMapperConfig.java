package com.ninedocs.serviceaggregator.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

@Configuration
public class ObjectMapperConfig {

  private static final DateTimeFormatter CUSTOM_FORMATTER
      = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    /*
     LocalDateTime 객체의 Json Serialization/Deserialization 커스터마이징
     */
    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addSerializer(LocalDateTime.class,
        new LocalDateTimeSerializer(CUSTOM_FORMATTER)
    );
    javaTimeModule.addDeserializer(LocalDateTime.class,
        new LocalDateTimeDeserializer(CUSTOM_FORMATTER)
    );
    mapper.registerModule(javaTimeModule);

    return mapper;
  }

  @Bean
  public Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper objectMapper) {
    return new Jackson2JsonEncoder(objectMapper);
  }

  @Bean
  public Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper objectMapper) {
    return new Jackson2JsonDecoder(objectMapper);
  }
}
