package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.exception.BusinessException;
import com.internship.paybycard.paymentprocess.core.domain.exception.InsufficientCardBalance;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidCardException;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.domain.dto.payment.response.InitiatePaymentUseCaseResponseImpl;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@Slf4j
@Getter
public class InitiatePaymentModelImpl implements InitiatePaymentModel {
    private final String items;
    private final BigDecimal amount;
    private final String clientName;
    private final VerifyCardDto card;
    private final PaymentDao paymentDao;
    private final CmsApiHandler cmsApiHandler;

    private CardDto verifiedCard;
    private boolean isPaymentValid;
    private ErrorCode errorCode;
    private InitiatePaymentUseCaseResponseImpl response;

    @Builder
    public InitiatePaymentModelImpl(String items, BigDecimal amount, String clientName,
                                    VerifyCardDto card, PaymentDao paymentDao, CmsApiHandler cmsApiHandler) {
        this.items = items;
        this.amount = amount;
        this.clientName = clientName;
        this.card = card;
        this.paymentDao = paymentDao;
        this.cmsApiHandler = cmsApiHandler;
        this.isPaymentValid = false;
    }

    @Override
    public void validatePayment() {
        try {
            log.debug("verifying with CMS api card: {}", card.getCardNumber());
            verifiedCard = cmsApiHandler.verifyCard(card);
            if (isNull(verifiedCard))
                throw new InvalidCardException("Card not exist", ErrorCode.INVALID_CARD);
            if (amount.compareTo(verifiedCard.getBalance()) > 0)
                throw new InsufficientCardBalance("the amount is bigger your card balance", ErrorCode.INSUFFICIENT_CARD_BALANCE);
            isPaymentValid = true;

        } catch (BusinessException e) {
            log.error("Business exception", e);
            errorCode = e.getErrorCode();
            isPaymentValid = false;
        } catch (Exception e) {
            log.error("unexpected error in initiatePayment: {}", e.getMessage(), e);
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            isPaymentValid = false;
        }
    }

    @Override
    public void process() {
        try {
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
                PaymentDto initiatedPayment = paymentDao.createPayment(realPaymentDto);
                response = new InitiatePaymentUseCaseResponseImpl(initiatedPayment.getReferenceNumber(), "initiated successfully");
                errorCode = ErrorCode.SUCCESS;
            }
        } catch (Exception e) {
            log.error("unexpected error in initiatePayment process : {}", e.getMessage(), e);
            isPaymentValid = false;
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public Result<InitiatePaymentUseCaseResponse> result() {
        if (response!=null)
            return new Result<>(Status.ACT, ErrorCode.SUCCESS, response);
        else
            return new Result<>(Status.RJC, errorCode, null);
    }

}
