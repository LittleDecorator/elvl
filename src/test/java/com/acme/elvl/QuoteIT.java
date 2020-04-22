package com.acme.elvl;

import com.acme.elvl.model.entity.Quote;
import com.acme.elvl.service.QuoteService;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class QuoteIT {

  @Autowired
  private QuoteService service;

  @AfterEach
  void clean(){
    service.clearAll();
  }

  @Test
  void persistSingleQuote() {
    Quote quote = new Quote().setIsin("RU000A0JX0J2").setBid(100.2).setAsk(101.9);
    service.persist(quote);
    Assertions.assertEquals(100.2, service.getLevel("RU000A0JX0J2"));
    Assertions.assertEquals(1, service.getLevels().size());
  }

  @Test
  void skipQuotesWithEmptyIsin() {
    Quote quote = new Quote().setBid(100.2).setAsk(101.9);
    service.persist(quote);
    Assertions.assertTrue(service.getLevels().isEmpty());
  }

  @Test
  void wrongQuoteIsinLength() {
    Quote quote = new Quote().setIsin("RU000").setBid(100.2).setAsk(101.9);
    service.persist(quote);
    Assertions.assertTrue(service.getLevels().isEmpty());
  }

  @Test
  void persistMultipleQuotesWithSameISIN() {
    ArrayList<Quote> quotes = new ArrayList<>(2);
    quotes.add(new Quote().setIsin("RU000A0JX0J2").setBid(100.2).setAsk(101.9));
    quotes.add(new Quote().setIsin("RU000A0JX0J2").setBid(100.5).setAsk(101.9));
    service.persist(quotes);
    Assertions.assertEquals(100.5, service.getLevel("RU000A0JX0J2"));
    Assertions.assertEquals(1, service.getLevels().size());
  }

  @Test
  void persistMultipleQuotesWithDifferentISIN() {
    ArrayList<Quote> quotes = new ArrayList<>();
    quotes.add(new Quote().setIsin("RU000A0JX0J2").setBid(100.2).setAsk(101.9));
    quotes.add(new Quote().setIsin("RU000A5HR0B4").setBid(90.5).setAsk(78.0));
    quotes.add(new Quote().setIsin("RU030K4H65B1").setAsk(123.7));

    service.persist(quotes);
    Assertions.assertEquals(123.7, service.getLevel("RU030K4H65B1"));
    Assertions.assertEquals(3, service.getLevels().size());
  }

}
