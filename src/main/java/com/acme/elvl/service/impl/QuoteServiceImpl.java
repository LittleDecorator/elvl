package com.acme.elvl.service.impl;

import com.acme.elvl.exception.NoSuchQuoteException;
import com.acme.elvl.model.entity.EnergyLevel;
import com.acme.elvl.model.entity.Quote;
import com.acme.elvl.repository.EnergyLevelRepository;
import com.acme.elvl.repository.QuoteRepository;
import com.acme.elvl.service.QuoteService;
import com.acme.elvl.util.StreamUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

  private final QuoteRepository quoteRepository;
  private final EnergyLevelRepository levelRepository;

  @Override
  public void persist(Quote quote) {
    persist(Collections.singleton(quote));
  }

  @Override
  public void persist(Collection<Quote> quotes) {
    Map<String, Set<Quote>> isinGroup = quotes.stream()
        //FIXME: Maybe a better approach will be throw exception if any quotes have invalid data
        .filter(StreamUtils.logFiltered(
            it -> {
              String isin = it.getIsin();
              return !StringUtils.isEmpty(isin) && isin.length() == 12;
            },
            it -> log.warn("Skipping quote={} because it's ISIN is empty or has an unacceptable length", it)
        ))
        .collect(Collectors.groupingBy(Quote::getIsin, Collectors.toSet()));
    isinGroup.forEach((k, v) -> {
      EnergyLevel energyLevel = levelRepository.findById(k).orElse(new EnergyLevel(k));
      v.stream().map(it -> {
        // add to history
        quoteRepository.save(it);
        // calculate energy level value
        return this.calculateLevel(energyLevel.getElvl(), it.getBid(), it.getAsk());
      }).max(Double::compare).ifPresent(it -> {
        if(energyLevel.getElvl() == null || energyLevel.getElvl() < it){
          // persist best result
          energyLevel.setElvl(it);
          levelRepository.save(energyLevel);
        }
      });
    });
  }

  @Override
  public Double getLevel(String isin) {
    return levelRepository.findById(isin).map(EnergyLevel::getElvl)
        .orElseThrow(() -> {
          String exMessage = MessageFormatter.format("Quote for isin: {} not exists", isin).getMessage();
          return new NoSuchQuoteException(exMessage);
        });
  }

  @Override
  public Set<Double> getLevels() {
    return StreamSupport.stream(levelRepository.findAll().spliterator(), false)
        .map(EnergyLevel::getElvl).collect(Collectors.toSet());
  }

  @Override
  public void clearAll() {
    levelRepository.deleteAll();
    quoteRepository.deleteAll();
  }

  private Double calculateLevel(Double elvl, Double bid, Double ask) {
    if (elvl == null) {
      if (bid == null) return ask;
      return bid;
    }
    if (bid > elvl) return bid;
    if (ask < elvl) return ask;
    return elvl;
  }

}
