package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.core.exception.CardNotFoundException;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardModel;
import com.internship.paybycard.cardmanagement.persistence.PersistenceTestApp;
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
    private CardDaoImpl cardDaoImpl;


    @Test
    public void testSaveCard() {
        CardModel savedCard = cardDaoImpl.saveCard(cardModel);
        assertNotNull(savedCard);
        assertEquals(cardModel.getBalance(), savedCard.getBalance());
        assertTrue(isEqualWithoutId(cardModel, savedCard));
    }

    @Test
    public void testFindCard() {
        cardDaoImpl.saveCard(cardModel);
        CardModel retrievedCard = cardDaoImpl.findCard(cardModel.getCardNumber(), cardModel.getCVV(), cardModel.getExpiryDate());
        assertNotNull(retrievedCard);
        System.out.println(cardModel.getBalance());
        assertTrue(isEqualWithoutId(cardModel, retrievedCard));
    }

    @Test
    public void testFindCard_invalidCardInfo() {
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard("", "", LocalDate.of(1994, 3, 4)));
        assertEquals("invalid card :", exception.getMessage());

        cardDaoImpl.saveCard(cardModel);
        Exception exception2 = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard(cardModel.getCardNumber(), "", LocalDate.of(1994, 3, 4)));
        assertEquals("invalid card :", exception2.getMessage());
    }

    @Test
    public void testDeleteCard() {
        cardDaoImpl.saveCard(cardModel);
        ValidateCardInteractor card = new ValidateCardInteractor() {

            @Override
            public String getCardNumber() {
                return cardModel.getCardNumber();
            }

            @Override
            public String getCVV() {
                return cardModel.getCVV();
            }

            @Override
            public LocalDate getExpiryDate() {
                return cardModel.getExpiryDate();
            }
        };
        cardDaoImpl.deleteCard(card);
        Exception exception = assertThrows(CardNotFoundException.class, () -> cardDaoImpl.findCard(cardModel.getCardNumber(), cardModel.getCVV(), cardModel.getExpiryDate()));
        assertEquals("invalid card :", exception.getMessage());
    }

    @Test
    public void testDeleteCard_invalidCardInfo() {
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
    public void testUpdateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate() {
        cardDaoImpl.saveCard(cardModel);
        UpdateCardInteractor card = new UpdateCardInteractor() {

            @Override
            public String getCvv() {
                return cardModel.getCVV();
            }

            @Override
            public String getCardNumber() {
                return cardModel.getCardNumber();
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
                return cardModel.getExpiryDate();
            }
        };
        cardDaoImpl.updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate(card);
        CardModel updatedCard=cardDaoImpl.findCard(cardModel.getCardNumber(), cardModel.getCVV(), cardModel.getExpiryDate());
        assertNotNull(updatedCard);
        assertFalse(isEqualWithoutId(cardModel, updatedCard));
        assertTrue(updatedCard.getClientEmail().equals("lala@email.com"));
        assertTrue(updatedCard.getClientName().equals("lala"));
        assertTrue(updatedCard.getBalance().compareTo(BigDecimal.valueOf(5000)) == 0);
    }

    @Test
    public void testUpdateCardBalanceAndClientEmailAndClientName_invalidCardInfo() {
        cardDaoImpl.saveCard(cardModel);
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
                return cardModel.getExpiryDate();
            }
        };
        Exception exception=assertThrows(RuntimeException.class,()->cardDaoImpl.updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate(card));
        assertEquals("invalid card info",exception.getMessage());
    }

    @Test
    public void testFindById(){
        CardModel savedCard=cardDaoImpl.saveCard(cardModel);
        CardModel retrievedCard= cardDaoImpl.findCardById(savedCard.getId());
        assertNotNull(retrievedCard);
        assertTrue(isEqualWithoutId(cardModel, retrievedCard));
    }


    @Test
    public void testFindById_invalidId(){
        Exception exception= assertThrows(CardNotFoundException.class,()->cardDaoImpl.findCardById(0L));
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

    private final CardModel cardModel = new CardModel() {
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

