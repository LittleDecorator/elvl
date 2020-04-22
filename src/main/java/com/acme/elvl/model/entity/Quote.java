package com.acme.elvl.model.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@RedisHash("Quote")
public class Quote {

  @Id
  @Indexed
  private String id;
  private String isin;
  private Double bid;
  private Double ask;
  @CreatedDate
  private LocalDateTime dateAdd;

}
