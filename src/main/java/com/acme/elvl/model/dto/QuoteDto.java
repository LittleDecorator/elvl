package com.acme.elvl.model.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
public class QuoteDto {

  @NotEmpty(message = "Parameter ISIN is required")
  @Length(min = 12, max = 12, message = "ISIN must be exactly 12 symbol length")
  private String isin;
  private Double bid;
  private Double ask;

}
