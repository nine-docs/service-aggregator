package com.ninedocs.serviceaggregator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class WebFluxConfig {

  private static final DateTimeFormatter CUSTOM_FORMATTER
      = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    /*
     LocalDateTime 객체의 Json Serialization 커스터마이징
     */
    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addSerializer(LocalDateTime.class,
        new LocalDateTimeSerializer(CUSTOM_FORMATTER)
    );
    mapper.registerModule(javaTimeModule);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO-8601 형식 사용 비활성화

    return mapper;
  }
}
