package com.merveadler.config.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableCaching // redis üzerinden cacheleme yapabilmek için spring cache i aktif ediyoruz.
@EnableRedisRepositories // redis repositorylerini kullanabilmek için gerekli olan anotasyon
public class RedisConfig {

//    @Value("${myapplication.redis.host}")
//    String redisHost;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }
}
