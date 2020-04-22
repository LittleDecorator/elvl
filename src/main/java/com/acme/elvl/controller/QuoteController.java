package com.acme.elvl.controller;

import com.acme.elvl.mapper.QuoteMapper;
import com.acme.elvl.model.dto.QuoteDto;
import com.acme.elvl.service.QuoteService;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteController {

  private final QuoteMapper mapper;
  private final QuoteService service;

  @PostMapping()
  public void saveQuotes( @RequestBody @NotEmpty(message = "Quotes list cannot be empty.") List<@Valid QuoteDto> dtos) {
    service.persist(mapper.dtoToModel(dtos));
  }

  @GetMapping("/energy")
  public Set<Double> getElvl() {
    return service.getLevels();
  }

  @GetMapping("/energy/{isin}")
  public Double getElvl(@PathVariable("isin") String isin) {
    return service.getLevel(isin);
  }

}
