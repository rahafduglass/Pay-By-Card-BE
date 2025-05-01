package com.internship.paybycard.cardmanagement;

import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.mapper.UpdateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.service.CardServiceImpl;
import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.service.CardService;
import com.internship.paybycard.cardmanagement.persistence.dao.CardDaoImpl;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.cardmanagement.persistence.mapper.CardEntityMapperImpl;
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

    @Bean
    public CardDao cardDao(CardJpaRepository cardJpaRepository, CardEntityMapperImpl cardEntityMapper) {
        return new CardDaoImpl(cardJpaRepository, cardEntityMapper);
    }
    @Bean
    public CardDaoImpl cardDaoImpl(CardJpaRepository cardJpaRepository,CardEntityMapperImpl cardEntityMapper) {
        return new CardDaoImpl(cardJpaRepository, cardEntityMapper);
    }
}
