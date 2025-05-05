package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.core.exception.CardNotFoundException;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardModel;
import com.internship.paybycard.cardmanagement.persistence.PersistenceTestApp;
import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.cardmanagement.persistence.mapper.CardEntityMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PersistenceTestApp.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CardDaoImplTest {

    @Autowired
    private CardJpaRepository cardJpaRepository;

    @Autowired
    private CardDaoImpl cardDaoImpl;

    @Autowired
    private CardEntityMapperImpl cardEntityMapper;

    @Test
    public void givenValidCard_whenCallSaveCardThenCallFindById_thenRetrievedCardEqualsSavedCard() {
        CardModel savedCard = cardDaoImpl.saveCard(validCardModel);
        CardEntity retrievedCard = cardJpaRepository.findById(savedCard.getId()).orElse(null);
        assertNotNull(retrievedCard);
        assertNotNull(savedCard);
        CardModel retrievedCardModel = cardEntityMapper.reverseTo(retrievedCard);
        assertTrue(isEqualWithoutId(retrievedCardModel, savedCard));
    }

    @Test
    public void givenValidCard_whenCallSaveCardThenCallFindCard_thenReturnSavedCard() {
        cardDaoImpl.saveCard(validCardModel);
        CardModel retrievedCard = cardDaoImpl.findCard(validCardModel.getCardNumber(), validCardModel.getCVV(), validCardModel.getExpiryDate());
        assertNotNull(retrievedCard);
        System.out.println(validCardModel.getBalance());
        assertTrue(isEqualWithoutId(validCardModel, retrievedCard));
    }

    @Test
    public void givenInvalidCardInfo_whenCallFindCard_thenThrowException() {
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard("", "", LocalDate.of(1994, 3, 4)));
        assertEquals("invalid card :", exception.getMessage());

        cardDaoImpl.saveCard(validCardModel);
        Exception exception2 = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard(validCardModel.getCardNumber(), "", LocalDate.of(1994, 3, 4)));
        assertEquals("invalid card :", exception2.getMessage());
    }

    @Test
    public void givenValidCard_whenCallDeleteCardThenTryToFindIt_thenThrowException() {
        cardDaoImpl.saveCard(validCardModel);
        ValidateCardInteractor card = new ValidateCardInteractor() {

            @Override
            public String getCardNumber() {
                return validCardModel.getCardNumber();
            }

            @Override
            public String getCVV() {
                return validCardModel.getCVV();
            }

            @Override
            public LocalDate getExpiryDate() {
                return validCardModel.getExpiryDate();
            }
        };
        cardDaoImpl.deleteCard(card);
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard(validCardModel.getCardNumber(), validCardModel.getCVV(), validCardModel.getExpiryDate()));
        assertEquals("invalid card :", exception.getMessage());
    }

    @Test
    public void givenInvalidCard_whenCallDeleteCard_thenThrowException() {
        ValidateCardInteractor invalidCard = new ValidateCardInteractor() {
            @Override
            public String getCardNumber() {
                return "";
            }

            @Override
            public String getCVV() {
                return "";
            }

            @Override
            public LocalDate getExpiryDate() {
                return null;
            }
        };
        Exception exception = assertThrows(RuntimeException.class, () -> cardDaoImpl.deleteCard(invalidCard));
        assertEquals("invalid card info", exception.getMessage());
    }

    @Test
    public void givenValidCardModel_whenSaveCardThenUpdateCard_thenReturnNothingAndUpdateCard() {
        cardDaoImpl.saveCard(validCardModel);
        UpdateCardInteractor card = new UpdateCardInteractor() {

            @Override
            public String getCvv() {
                return validCardModel.getCVV();
            }

            @Override
            public String getCardNumber() {
                return validCardModel.getCardNumber();
            }

            @Override
            public String getClientEmail() {
                return "lala@email.com";
            }

            @Override
            public String getClientName() {
                return "lala";
            }

            @Override
            public BigDecimal getBalance() {
                return BigDecimal.valueOf(5000);
            }

            @Override
            public LocalDate getExpiryDate() {
                return validCardModel.getExpiryDate();
            }
        };
        cardDaoImpl.updateCardInfo(card);
        CardModel updatedCard = cardDaoImpl.findCard(validCardModel.getCardNumber(), validCardModel.getCVV(), validCardModel.getExpiryDate());
        assertNotNull(updatedCard);
        assertFalse(isEqualWithoutId(validCardModel, updatedCard));
        assertEquals("lala@email.com", updatedCard.getClientEmail());
        assertEquals("lala", updatedCard.getClientName());
        assertEquals(0, updatedCard.getBalance().compareTo(BigDecimal.valueOf(5000)));
    }

    @Test
    public void givenInvalidCardModel_whenCallUpdateCardInfo_thenThrowException() {
        cardDaoImpl.saveCard(validCardModel);
        UpdateCardInteractor card = new UpdateCardInteractor() {

            @Override
            public String getCvv() {
                return "";
            }

            @Override
            public String getCardNumber() {
                return "";
            }

            @Override
            public String getClientEmail() {
                return "lala@email.com";
            }

            @Override
            public String getClientName() {
                return "lala";
            }

            @Override
            public BigDecimal getBalance() {
                return BigDecimal.valueOf(5000);
            }

            @Override
            public LocalDate getExpiryDate() {
                return validCardModel.getExpiryDate();
            }
        };
        Exception exception = assertThrows(RuntimeException.class, () -> cardDaoImpl.updateCardInfo(card));
        assertEquals("invalid card info", exception.getMessage());
    }

    @Test
    public void givenValidCardId_whenCallFindById_thenReturnSavedCard() {
        CardModel savedCard = cardDaoImpl.saveCard(validCardModel);
        CardModel retrievedCard = cardDaoImpl.findCardById(savedCard.getId());
        assertNotNull(retrievedCard);
        assertTrue(isEqualWithoutId(validCardModel, retrievedCard));
    }

    @Test
    public void givenInvalidCardId_whenCallFindById_thenThrowException() {
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCardById(0L));
        assertEquals("couldn't find card with id", exception.getMessage());
    }

    private boolean isEqualWithoutId(CardModel cardModel, CardModel savedCard) {
        return (cardModel.getCardNumber().equals(savedCard.getCardNumber())
                && cardModel.getCVV().equals(savedCard.getCVV())
                && cardModel.getClientName().equals(savedCard.getClientName())
                && cardModel.getClientEmail().equals(savedCard.getClientEmail())
                && cardModel.getExpiryDate().equals(savedCard.getExpiryDate())
                && cardModel.getBalance().compareTo(savedCard.getBalance()) == 0);
    }

    private final CardModel validCardModel = new CardModel() {
        private final UUID cardNumber = UUID.randomUUID();

        @Override
        public boolean isNull() {
            return false;
        }

        @Override
        public Long getId() {
            return 1L;
        }

        @Override
        public BigDecimal getBalance() {
            return BigDecimal.valueOf(300000);
        }

        @Override
        public LocalDate getExpiryDate() {
            return LocalDate.now().plusYears(2);
        }

        @Override
        public String getClientEmail() {
            return "client@email.com";
        }

        @Override
        public String getClientName() {
            return "client";
        }

        @Override
        public String getCVV() {
            return "123";
        }

        @Override
        public String getCardNumber() {
            return cardNumber.toString();
        }

        @Override
        public boolean equals(CardModel cardModel) {
            return cardModel.getId().equals(this.getId())
                    && cardModel.getCardNumber().equals(this.getCardNumber())
                    && cardModel.getCVV().equals(this.getCVV())
                    && cardModel.getClientName().equals(this.getClientName())
                    && cardModel.getClientEmail().equals(this.getClientEmail())
                    && cardModel.getExpiryDate().equals(this.getExpiryDate())
                    && cardModel.getBalance().equals(this.getBalance());
        }
    };

}

