package com.acme.elvl.components;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

@Component
public class RedisEmbeddedComponent {

  private RedisServer redis;

  @Value("${spring.redis.port}")
  private int redisPort;

  @PostConstruct
  public void startRedis() {
    redis = RedisServer.builder()
        .port(redisPort)
        .setting("maxmemory 128M") //otherwise not start on windows
        .build();
    redis.start();
  }

  @PreDestroy
  public void stopRedis() {
    redis.stop();
  }
}
