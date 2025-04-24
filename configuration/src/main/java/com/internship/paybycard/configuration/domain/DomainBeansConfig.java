package com.internship.paybycard.configuration.domain;

import com.internship.paybycard.cardmanagement.domain.service.CardServiceImpl;
import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.mapper.UpdateCardMapperImpl;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.logger.CardServiceLogger;
import com.internship.paybycard.core.service.CardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeansConfig{


    @Bean
    public CardService cardService(CardDao cardDao) {
        return new CardServiceImpl(cardDao, createCardMapperImpl());
    }

    @Bean
    public CreateCardMapperImpl createCardMapperImpl() {
        return new CreateCardMapperImpl();
    }

    @Bean
    public UpdateCardMapperImpl updateCardMapperImpl() {
        return new UpdateCardMapperImpl();
    }


}
