package com.acme.elvl.mapper;

import com.acme.elvl.model.dto.QuoteDto;
import com.acme.elvl.model.entity.Quote;
import java.util.Collection;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper
public interface QuoteMapper {

  Quote dtoToModel(QuoteDto dto);

  @IterableMapping(elementTargetType = Quote.class)
  List<Quote> dtoToModel(Collection<QuoteDto> dto);
}
