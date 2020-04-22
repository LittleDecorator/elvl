package com.acme.elvl.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@RedisHash("EnergyLevel")
public class EnergyLevel {

  @Id
  private String isin;
  private Double elvl;

  public EnergyLevel(String isin) {
    this.isin = isin;
  }

}
