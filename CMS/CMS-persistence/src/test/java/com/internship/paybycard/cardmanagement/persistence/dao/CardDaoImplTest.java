package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.core.exception.CardNotFoundException;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardDto;
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
        CardDto savedCard = cardDaoImpl.saveCard(validCardDto);
        CardEntity retrievedCard = cardJpaRepository.findById(savedCard.getId()).orElse(null);
        assertNotNull(retrievedCard);
        assertNotNull(savedCard);
        CardDto retrievedCardDto = cardEntityMapper.reverseTo(retrievedCard);
        assertTrue(isEqualWithoutId(retrievedCardDto, savedCard));
    }

    @Test
    public void givenValidCard_whenCallSaveCardThenCallFindCard_thenReturnSavedCard() {
        cardDaoImpl.saveCard(validCardDto);
        CardDto retrievedCard = cardDaoImpl.findCard(validCardDto.getCardNumber(), validCardDto.getCVV(), validCardDto.getExpiryDate());
        assertNotNull(retrievedCard);
        System.out.println(validCardDto.getBalance());
        assertTrue(isEqualWithoutId(validCardDto, retrievedCard));
    }

    @Test
    public void givenInvalidCardInfo_whenCallFindCard_thenThrowException() {
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard("", "", LocalDate.of(1994, 3, 4)));
        assertEquals("invalid card :", exception.getMessage());

        cardDaoImpl.saveCard(validCardDto);
        Exception exception2 = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard(validCardDto.getCardNumber(), "", LocalDate.of(1994, 3, 4)));
        assertEquals("invalid card :", exception2.getMessage());
    }

    @Test
    public void givenValidCard_whenCallDeleteCardThenTryToFindIt_thenThrowException() {
        cardDaoImpl.saveCard(validCardDto);
        ValidateCardInteractor card = new ValidateCardInteractor() {

            @Override
            public String getCardNumber() {
                return validCardDto.getCardNumber();
            }

            @Override
            public String getCVV() {
                return validCardDto.getCVV();
            }

            @Override
            public LocalDate getExpiryDate() {
                return validCardDto.getExpiryDate();
            }
        };
        cardDaoImpl.deleteCard(card);
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard(validCardDto.getCardNumber(), validCardDto.getCVV(), validCardDto.getExpiryDate()));
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
        cardDaoImpl.saveCard(validCardDto);
        UpdateCardInteractor card = new UpdateCardInteractor() {

            @Override
            public String getCvv() {
                return validCardDto.getCVV();
            }

            @Override
            public String getCardNumber() {
                return validCardDto.getCardNumber();
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
                return validCardDto.getExpiryDate();
            }
        };
        cardDaoImpl.updateCardInfo(card);
        CardDto updatedCard = cardDaoImpl.findCard(validCardDto.getCardNumber(), validCardDto.getCVV(), validCardDto.getExpiryDate());
        assertNotNull(updatedCard);
        assertFalse(isEqualWithoutId(validCardDto, updatedCard));
        assertEquals("lala@email.com", updatedCard.getClientEmail());
        assertEquals("lala", updatedCard.getClientName());
        assertEquals(0, updatedCard.getBalance().compareTo(BigDecimal.valueOf(5000)));
    }

    @Test
    public void givenInvalidCardModel_whenCallUpdateCardInfo_thenThrowException() {
        cardDaoImpl.saveCard(validCardDto);
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
                return validCardDto.getExpiryDate();
            }
        };
        Exception exception = assertThrows(RuntimeException.class, () -> cardDaoImpl.updateCardInfo(card));
        assertEquals("invalid card info", exception.getMessage());
    }

    @Test
    public void givenValidCardId_whenCallFindById_thenReturnSavedCard() {
        CardDto savedCard = cardDaoImpl.saveCard(validCardDto);
        CardDto retrievedCard = cardDaoImpl.findCardById(savedCard.getId());
        assertNotNull(retrievedCard);
        assertTrue(isEqualWithoutId(validCardDto, retrievedCard));
    }

    @Test
    public void givenInvalidCardId_whenCallFindById_thenThrowException() {
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCardById(0L));
        assertEquals("couldn't find card with id", exception.getMessage());
    }

    private boolean isEqualWithoutId(CardDto cardDto, CardDto savedCard) {
        return (cardDto.getCardNumber().equals(savedCard.getCardNumber())
                && cardDto.getCVV().equals(savedCard.getCVV())
                && cardDto.getClientName().equals(savedCard.getClientName())
                && cardDto.getClientEmail().equals(savedCard.getClientEmail())
                && cardDto.getExpiryDate().equals(savedCard.getExpiryDate())
                && cardDto.getBalance().compareTo(savedCard.getBalance()) == 0);
    }

    private final CardDto validCardDto = new CardDto() {
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
        public boolean equals(CardDto cardDto) {
            return cardDto.getId().equals(this.getId())
                    && cardDto.getCardNumber().equals(this.getCardNumber())
                    && cardDto.getCVV().equals(this.getCVV())
                    && cardDto.getClientName().equals(this.getClientName())
                    && cardDto.getClientEmail().equals(this.getClientEmail())
                    && cardDto.getExpiryDate().equals(this.getExpiryDate())
                    && cardDto.getBalance().equals(this.getBalance());
        }
    };

}

