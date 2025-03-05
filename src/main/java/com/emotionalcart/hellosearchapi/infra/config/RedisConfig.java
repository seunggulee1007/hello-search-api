package com.emotionalcart.hellosearchapi.infra.config;

import com.emotionalcart.hellosearchapi.domain.rabbitmq.OrderSaveRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, OrderSaveRequest> redisOrderTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, OrderSaveRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // Jackson Serializer 설정
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())  // LocalDateTime 등 지원
            .activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL
            );

        Jackson2JsonRedisSerializer<OrderSaveRequest> serializer =
            new Jackson2JsonRedisSerializer<>(objectMapper, OrderSaveRequest.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, Integer> redisSalesTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        setSerialize(template);
        return template;
    }

    private void setSerialize(RedisTemplate<String, ?> template) {
        // JSON 직렬화 설정
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(new ObjectMapper()
                                                                                                   .registerModule(new JavaTimeModule()) // LocalDateTime 지원
                                                                                                   .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));

        template.setKeySerializer(new StringRedisSerializer());  // 키 직렬화 방식 (문자열)
        template.setValueSerializer(serializer);  // 값 직렬화 방식 (JSON)
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
    }

}
