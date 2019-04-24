package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;


import com.crud.tasks.trello.validator.TrelloValidator;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Component
public class TrelloFacade {

    private static final Logger LOGGER= LoggerFactory.logger(TrelloFacade.class);

    @Autowired
    private TrelloService trelloService;

    @Autowired
    private TrelloMapper trelloMapper;

    @Autowired
    private TrelloValidator trelloValidator;

    public List<TrelloBoardDto> fetchTrelloBoards()
    {
        List<TrelloBoard> trelloBoards=trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards=trelloValidator.validateTrelloBoards(trelloBoards);
        LOGGER.info("Boards have been filtered. Current list size: "+filteredBoards.size());
        return trelloMapper.mapToBoardsDto(filteredBoards);
    }

    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCardDto)
    {
        TrelloCard trelloCard=trelloMapper.mapToCard(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        return trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
}
