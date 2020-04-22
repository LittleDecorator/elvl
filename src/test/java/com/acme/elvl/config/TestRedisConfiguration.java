package com.acme.elvl.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfiguration {

  private RedisServer redisServer;

  public TestRedisConfiguration(@Value("${spring.redis.port}") int redisPort) {
    this.redisServer = RedisServer.builder()
        .port(redisPort)
        .setting("maxmemory 128M") //maxheap 128M
        .build();
  }

  @PostConstruct
  public void postConstruct() {
    redisServer.start();
  }

  @PreDestroy
  public void preDestroy() {
    redisServer.stop();
  }
}
