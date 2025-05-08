package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.InsufficientCardBalance;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Getter
public class InitiatePaymentModelImpl implements InitiatePaymentModel {
    private final String items;
    private final BigDecimal amount;
    private final String clientName;
    private final VerifyCardDto card;
    private boolean isPaymentValid = false;
    private CardDto verifiedCard;

    private final PaymentDao paymentDao;
    private final CmsApiHandler cmsApiHandler;

    @Override
    public boolean validatePayment() {
        log.debug("verifying with CMS api card: {}", card.getCardNumber());
        verifiedCard = cmsApiHandler.verifyCard(card);
        if (amount.compareTo(verifiedCard.getBalance()) > 0) {
            throw new InsufficientCardBalance("the amount is bigger your card balance", ErrorCode.INSUFFICIENT_CARD_BALANCE);
        }

        isPaymentValid = true;
        return true;
    }

    @Override
    public PaymentDto process() {
        if (isPaymentValid) {
            log.info("processing initiate payment with reference number {}", card.getCardNumber());
            RealPaymentDto realPaymentDto = new RealPaymentDto();
            realPaymentDto.setAmount(amount);
            realPaymentDto.setItems(items);
            realPaymentDto.setClientName(clientName);
            realPaymentDto.setClientEmail(verifiedCard.getClientEmail());
            realPaymentDto.setCardNumber(verifiedCard.getCardNumber());
            realPaymentDto.setConfirmed(false);
            realPaymentDto.setReferenceNumber(UUID.randomUUID().toString());
            log.debug("creating payment record in database: {}", realPaymentDto);
            return paymentDao.createPayment(realPaymentDto);
        } else {
            log.error("Payment is not valid ");
            throw new RuntimeException("Payment is not valid: consider calling validatePayment() method first");
        }
    }

    public static ModelBuilder builder() {
        return new ModelBuilder();
    }

    private InitiatePaymentModelImpl(String items, BigDecimal amount, String clientName, VerifyCardDto card, PaymentDao paymentDao, CmsApiHandler cmsApiHandler) {
        this.items = items;
        this.amount = amount;
        this.clientName = clientName;
        this.card = card;
        this.paymentDao = paymentDao;
        this.cmsApiHandler = cmsApiHandler;
    }

    public static class ModelBuilder {
        private String items;
        private BigDecimal amount;
        private String clientName;
        private VerifyCardDto card;


        private PaymentDao paymentDao;
        private CmsApiHandler cmsApiHandler;

        public ModelBuilder items(String items) {
            this.items = items;
            return this;
        }

        public ModelBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public ModelBuilder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public ModelBuilder card(VerifyCardDto card) {
            this.card = card;
            return this;
        }

        public ModelBuilder paymentDao(PaymentDao paymentDao) {
            this.paymentDao = paymentDao;
            return this;
        }

        public ModelBuilder cmsApiHandler(CmsApiHandler cmsApiHandler) {
            this.cmsApiHandler = cmsApiHandler;
            return this;
        }

        public InitiatePaymentModelImpl build() {
            return new InitiatePaymentModelImpl(items, amount, clientName, card, paymentDao, cmsApiHandler);
        }

    }

}
