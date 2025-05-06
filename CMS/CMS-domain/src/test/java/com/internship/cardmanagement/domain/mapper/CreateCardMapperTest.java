package com.internship.cardmanagement.domain.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.mapper.CreateCardMapper;
import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateCardMapperTest {

    CreateCardMapperImpl cardMapper = new CreateCardMapperImpl();

    @Test
    void testMapTo(){
        CreateCardInteractor card = mock(CreateCardInteractor.class);
        when(card.getClientEmail()).thenReturn("client@email.com");
        when(card.getClientName()).thenReturn("clientName");
        when(card.getBalance()).thenReturn(BigDecimal.valueOf(30023423.0));

        RealCardModel cardModel = new RealCardModel();

        cardModel=cardMapper.mapTo(card);
        assertEquals("client@email.com",cardModel.getClientEmail());
        assertEquals("clientName",cardModel.getClientName());
        assertEquals(BigDecimal.valueOf(30023423.0),cardModel.getBalance());

    }
}
