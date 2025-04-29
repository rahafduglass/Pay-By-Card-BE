package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidPaymentException;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.PaymentDao;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.CardModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.domain.dto.PaymentDtoImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class InitiatePaymentModelImpl implements InitiatePaymentModel {
    private String items;
    private BigDecimal amount;
    private String clientName;
    private VerifyCardDto card;



    private final PaymentDao paymentDao;
    private final CmsApiHandler cmsApiHandler;


    @Override
    public PaymentDto process() {
        validatePayment();
        CardModel verifiedCard = cmsApiHandler.verifyCard(card);

        PaymentDto paymentDto = new PaymentDtoImpl();
        paymentDto.setAmount(amount);
        paymentDto.setItems(items);
        paymentDto.setClientName(clientName);
        paymentDto.setClientEmail(verifiedCard.getClientEmail());
        paymentDto.setCardNumber(verifiedCard.getCardNumber());
        paymentDto.setConfirmed(false);
        return paymentDao.createPayment(paymentDto);
    }


    private void validatePayment() {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0
                || items == null || items.equals("")
                || clientName == null || clientName.equals("")) {
            throw new InvalidPaymentException("invalid payment input");
        }
        validateCard();
    }

    private void validateCard() {
        if (card == null
                || card.getCardNumber() == null || card.getCardNumber().equals("")
                || card.getExpiryDate() == null
                || card.getCVV() == null || card.getCVV().equals("")) {
            throw new InvalidPaymentException("invalid card input");
        }
    }


}
