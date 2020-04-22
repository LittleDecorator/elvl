package com.acme.elvl.service;

import com.acme.elvl.model.entity.Quote;
import java.util.Collection;
import java.util.Set;

public interface QuoteService {

  void persist(Collection<Quote> quotes);

  void persist(Quote quote);

  Double getLevel(String isin);

  Set<Double> getLevels();

  void clearAll();

}
