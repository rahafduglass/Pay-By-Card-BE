package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidPaymentException;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.model.PaymentModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.PaymentDao;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.CardModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.service.CmsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class InitiatePaymentModelImpl implements InitiatePaymentModel {
    private String items;
    private BigDecimal amount;
    private String clientName;
    private VerifyCardModel card;



    private final PaymentDao paymentDao;
    private final CmsService cmsService;


    @Override
    public PaymentModel process() {
        validatePayment();
        CardModel verifiedCard = cmsService.verifyCard(card);

        PaymentModel paymentModel = new PaymentModelImpl();
        paymentModel.setAmount(amount);
        paymentModel.setItems(items);
        paymentModel.setClientName(clientName);
        paymentModel.setClientEmail(verifiedCard.getClientEmail());
        paymentModel.setCardNumber(verifiedCard.getCardNumber());
        paymentModel.setConfirmed(false);
        return paymentDao.createPayment(paymentModel);
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
