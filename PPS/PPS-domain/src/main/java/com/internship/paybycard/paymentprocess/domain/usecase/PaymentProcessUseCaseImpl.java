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
            log.debug("finish: mapping initiatePayment, mapped model");
            log.debug("validate payment: {}", initiatePaymentModel);
            initiatePaymentModel.validatePayment();
            log.debug("initiate payment: {}", initiatePaymentModel);
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
//            todo modify log messages similar to initiate payment
            log.debug("mapping command to model, mapped model: {}", verifyPaymentModel);
            log.debug("verifying payment with model verifyPayment(): {}", verifyPaymentModel);
            verifyPaymentModel.verifyPayment();
            log.debug("sending OTP to email with the following reference number: : {}", verifyPaymentModel.getReferenceNumber());
            verifyPaymentModel.sendOtp();
            return new Result<>(Status.ACT, ErrorCode.NULL, null);
        } catch (BusinessException e) {
            log.error("Business exception", e);
            return new Result<>(Status.RJC, e.getErrorCode(), null);
        } catch (Exception e) {
            log.error("unexpected error in verifyPayment: {}", e.getMessage(), e);
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<Void> completePayment(CompletePaymentCommand command) {
        log.info("Complete payment use case with command: {}", command);
        try {
            log.debug("mapping command to model,command: {}", command);
            CompletePaymentModel completePaymentModel = completePaymentModelMapper.commandToModel(command);
            //            todo modify log messages similar to initiate payment
            log.debug("mapping command to model, mapped model: {}", completePaymentModel);
            log.debug("verifying OTP for paymentModel: {}", completePaymentModel);
            completePaymentModel.verifyOTP();
            log.debug("perform payment using with pay(): paymentModel: {}", completePaymentModel);
            completePaymentModel.pay();
            return new Result<>(Status.ACT, ErrorCode.NULL, null);
        } catch (BusinessException e) {
            log.error("Business exception", e);
            return new Result<>(Status.RJC, e.getErrorCode(), null);
        } catch (Exception e) {
            log.error("unexpected error in completePayment : {}", e.getMessage(), e);
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
