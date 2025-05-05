package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidPaymentException;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @Override
    public PaymentDto initiate() {
        if (isPaymentValid) {
            CardDto verifiedCard=cmsApiHandler.verifyCard(card);

            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setAmount(amount);
            paymentDto.setItems(items);
            paymentDto.setClientName(clientName);
            paymentDto.setClientEmail(verifiedCard.getClientEmail());
            paymentDto.setCardNumber(verifiedCard.getCardNumber());
            paymentDto.setConfirmed(false);
            paymentDto.setReferenceNumber(UUID.randomUUID().toString());
            return paymentDao.createPayment(paymentDto);
        }else throw new RuntimeException("Payment is not valid: consider calling validatePayment() method first");
    }

    @Override
    public boolean validatePayment() {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0
                || items == null || items.isEmpty()
                || clientName == null || clientName.isEmpty()) {
            throw new InvalidPaymentException("invalid payment input");
        }
        validateCard();
        isPaymentValid = true;
        return true;
    }

    private void validateCard() {
        if (card == null
                || card.getCardNumber() == null || card.getCardNumber().equals("")
                || card.getExpiryDate() == null
                || card.getCVV() == null || card.getCVV().isEmpty()) {
            throw new InvalidPaymentException("invalid card input");
        }
    }


}
