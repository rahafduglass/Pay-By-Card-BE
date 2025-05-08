package com.internship.paybycard.paymentprocess.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.exception.BusinessException;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.CompletePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.VerifyPaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class PaymentProcessUseCaseImpl implements PaymentProcessUseCase {
    private final InitiatePaymentModelMapper initiatePaymentModelMapper;
    private final VerifyPaymentModelMapper verifyPaymentModelMapper;
    private final CompletePaymentModelMapper completePaymentModelMapper;
    private final Logger log = LoggerFactory.getLogger(PaymentProcessUseCaseImpl.class);

    @Override
    public Result<String> initiatePayment(InitiatePaymentCommand command) {
        log.info("Initiate payment use case with command: {}", command);
        try {
            InitiatePaymentModel initiatePaymentModel = initiatePaymentModelMapper.commandToModel(command);
            log.debug("finish: mapping initiatePayment");
            log.debug("validating payment:");
            initiatePaymentModel.validatePayment();
            log.debug("processing initiate payment");
            PaymentDto initiatedPayment = initiatePaymentModel.process();
            return new Result<>(Status.ACT, ErrorCode.NULL, initiatedPayment.getReferenceNumber());
        } catch (BusinessException e) {
            log.error("Business exception", e);
            return new Result<>(Status.RJC, e.getErrorCode(), null);
        } catch (Exception e) {
            log.error("unexpected error in initiatePayment: {}", e.getMessage(), e);
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<Void> verifyPayment(VerifyPaymentCommand command) {
        try {
            log.info("Verify payment use case with command: {}", command);
            VerifyPaymentModel verifyPaymentModel = verifyPaymentModelMapper.commandToModel(command);
            log.debug("finish: mapping command to model");
            log.debug("verifying payment: ");
            verifyPaymentModel.verifyPayment();
            log.debug("sending OTP to email with the following reference number: : {}", verifyPaymentModel.getReferenceNumber());
            verifyPaymentModel.sendOtp();
            return new Result<>(Status.ACT, ErrorCode.NULL, null);
        } catch (BusinessException e) {
            log.error("Business exception", e);
            return new Result<>(Status.RJC, e.getErrorCode(), null);
        } catch (Exception e) {
            log.error("unexpected error in verifyPayment(): {}", e.getMessage(), e);
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<Void> completePayment(CompletePaymentCommand command) {
        log.info("Complete payment use case with command: {}", command);
        try {
            log.debug("mapping command to model,command: {}", command);
            CompletePaymentModel completePaymentModel = completePaymentModelMapper.commandToModel(command);
            log.debug("finish mapping command to model");
            log.debug("verifying OTP for paymentModel");
            completePaymentModel.verifyOTP();
            log.debug("perform payment using with pay()");
            completePaymentModel.pay();
            return new Result<>(Status.ACT, ErrorCode.NULL, null);
        } catch (BusinessException e) {
            log.error("Business exception", e);
            return new Result<>(Status.RJC, e.getErrorCode(), null);
        } catch (Exception e) {
            log.error("unexpected error in completePayment() : {}", e.getMessage(), e);
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
