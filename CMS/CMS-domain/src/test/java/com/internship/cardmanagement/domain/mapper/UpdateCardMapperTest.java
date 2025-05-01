package com.internship.cardmanagement.domain.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.domain.mapper.UpdateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateCardMapperTest {

    UpdateCardMapperImpl updateCardMapper = new UpdateCardMapperImpl();

    @Test
    void testMapToCardModel(){
        UpdateCardInteractor card= mock(UpdateCardInteractor.class);
        when(card.getExpiryDate()).thenReturn(LocalDate.now());
        when(card.getCardNumber()).thenReturn("cardNumber");
        when(card.getClientEmail()).thenReturn("client@email.com");
        when(card.getClientName()).thenReturn("clientName");
        when(card.getCvv()).thenReturn("cvv");
        when(card.getBalance()).thenReturn(BigDecimal.valueOf(300000));

        RealCardModel cardModel = new RealCardModel();

        cardModel=updateCardMapper.mapTo(card);
        assertEquals(LocalDate.now(),cardModel.getExpiryDate());
        assertEquals("cardNumber",cardModel.getCardNumber());
        assertEquals("client@email.com",cardModel.getClientEmail());
        assertEquals("clientName",cardModel.getClientName());
        assertEquals(BigDecimal.valueOf(300000),cardModel.getBalance());
        assertEquals("cvv",cardModel.getCVV());

    }
}
