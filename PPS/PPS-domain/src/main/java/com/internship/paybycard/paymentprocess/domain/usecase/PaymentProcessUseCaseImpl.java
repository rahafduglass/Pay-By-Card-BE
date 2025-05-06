package com.internship.paybycard.paymentprocess.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.exception.*;
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
            log.debug("mapping command to model, mapped model: {}", initiatePaymentModel);

            log.debug("validating mode: {}", initiatePaymentModel);
            initiatePaymentModel.validatePayment();
            log.debug("initiating payment: {}", initiatePaymentModel);
            PaymentDto initiatedPayment = initiatePaymentModel.initiate();
            return new Result<>(Status.ACT, ErrorCode.NULL, initiatedPayment.getReferenceNumber());
        } catch (IllegalArgumentException e) {
            log.error("invalid command argument: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_COMMAND_INPUT, null);
        } catch (InvalidPaymentException e) {
            log.error("invalid payment input: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_PAYMENT_INPUT, null);
        } catch (InsufficientCardBalance e) {
            log.error("insufficient card balance: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INSUFFICIENT_CARD_BALANCE, null);
        } catch (InvalidCardException e) {
            log.error("invalid card: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_CARD, null);
        } catch (Exception e) {
            log.error("unexpected error: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }

    }

    @Override
    public Result<Void> verifyPayment(VerifyPaymentCommand command) {
        try {
            log.info("Verify payment use case with command: {}", command);
            VerifyPaymentModel verifyPaymentModel = verifyPaymentModelMapper.commandToModel(command);
            log.debug("mapping command to model, mapped model: {}", verifyPaymentModel);
            log.debug("verifying payment with model verifyPayment(): {}", verifyPaymentModel);
            verifyPaymentModel.verifyPayment();
            log.debug("sending OTP to email with the following reference number: : {}", verifyPaymentModel.getReferenceNumber());
            verifyPaymentModel.sendOtp();
            return new Result<>(Status.ACT, ErrorCode.NULL, null);
        } catch (IllegalArgumentException e) {
            log.error("invalid command argument: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_COMMAND_INPUT, null);
        } catch (EmptyReferenceNumberException e) {
            log.error("empty reference number: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.EMPTY_REFERENCE_NUMBER, null);
        } catch (PaymentNotFoundException e) {
            log.error("payment not found: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.PAYMENT_NOT_FOUND, null);
        } catch (Exception e) {
            log.error("unexpected error: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<Void> completePayment(CompletePaymentCommand command) {
        log.info("Complete payment use case with command: {}", command);
        try{
            log.debug("mapping command to model,command: {}", command);
            CompletePaymentModel completePaymentModel=completePaymentModelMapper.commandToModel(command);
            log.debug("mapping command to model, mapped model: {}", completePaymentModel);
            log.debug("verifying OTP for paymentModel: {}", completePaymentModel);
            completePaymentModel.verifyOTP();
            log.debug("perform payment using with pay(): paymentModel: {}", completePaymentModel);
            completePaymentModel.pay();
            return new Result<>(Status.ACT, ErrorCode.NULL, null);
        }catch (EmptyOtpException e){
            log.error("empty otp: {}", e.getMessage());
            return new Result<>(Status.RJC,ErrorCode.EMPTY_OTP,null);
        }catch (InvalidOtpException e){
            log.error("invalid otp: {}", e.getMessage());
            return new Result<>(Status.RJC,ErrorCode.INVALID_OR_EXPIRED_OTP,null);
        }catch (InvalidCardException e){
            log.error("invalid card: {}", e.getMessage());
            return new Result<>(Status.RJC,ErrorCode.INVALID_CARD,null);
        }
        catch (Exception e) {
            log.error("unexpected error: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
