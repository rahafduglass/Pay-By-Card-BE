package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidPaymentException;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.InsufficientCardBalance;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class InitiatePaymentModelImpl implements InitiatePaymentModel {
    private final String items;
    private final BigDecimal amount;
    private final String clientName;
    private final VerifyCardDto card;


    private final PaymentDao paymentDao;
    private final CmsApiHandler cmsApiHandler;
    private boolean isPaymentValid = false;

    private final Logger log = LoggerFactory.getLogger(InitiatePaymentModelImpl.class);


    @Override
    public boolean validatePayment() {
        log.info("Validating payment with reference number {}", card.getCardNumber());
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0
                || items == null || items.isEmpty()
                || clientName == null || clientName.isEmpty()) {
            log.error("Invalid payment data");
            throw new InvalidPaymentException("invalid payment input");
        }
        log.debug("validating payment.card with cardNumber= {}", card.getCardNumber());
        validateCard();
        isPaymentValid = true;
        return true;
    }

    @Override
    public PaymentDto initiate() {
        if (isPaymentValid) {
            log.info("Initiating payment with reference number {}", card.getCardNumber());
            log.debug("verifying with CMS api card: {}", card.getCardNumber());
            CardDto verifiedCard = cmsApiHandler.verifyCard(card);

            if (amount.compareTo(verifiedCard.getBalance()) > 0) {
                throw new InsufficientCardBalance("the amount is bigger your card balance");
            }

            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setAmount(amount);
            paymentDto.setItems(items);
            paymentDto.setClientName(clientName);
            paymentDto.setClientEmail(verifiedCard.getClientEmail());
            paymentDto.setCardNumber(verifiedCard.getCardNumber());
            paymentDto.setConfirmed(false);
            paymentDto.setReferenceNumber(UUID.randomUUID().toString());
            log.debug("creating payment record in database: {}", paymentDto);
            return paymentDao.createPayment(paymentDto);
        } else {
            log.error("Payment is not valid ");
            throw new RuntimeException("Payment is not valid: consider calling validatePayment() method first");
        }
    }


    private void validateCard() {
        log.debug("validating card input");
        if (card == null
                || card.getCardNumber() == null || card.getCardNumber().isEmpty()
                || card.getExpiryDate() == null
                || card.getCVV() == null || card.getCVV().isEmpty()) {
            log.error("Invalid card input");
            throw new InvalidPaymentException("invalid card input");
        }
    }


}
