package com.internship.paybycard.paymentprocess.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.RequestPaymentVerificationCommand;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidCardException;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidPaymentException;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
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
    private final Logger log = LoggerFactory.getLogger(PaymentProcessUseCaseImpl.class);

    @Override
    public  Result<String> initiatePayment(InitiatePaymentCommand command) {
        log.info("Initiate payment use case command: {}", command);
        try {
            InitiatePaymentModel initiatePaymentModel = initiatePaymentModelMapper.commandToModel(command);
            log.debug("mapping command to model: {}", initiatePaymentModel);

            log.debug("validating mode: {}", initiatePaymentModel);
            initiatePaymentModel.validatePayment();
            log.debug("initiating payment: {}", initiatePaymentModel);
            PaymentDto initiatedPayment=initiatePaymentModel.initiate();
            return new Result<String>(Status.ACT, ErrorCode.NULL,initiatedPayment.getReferenceNumber());
        } catch (IllegalArgumentException e) {
            log.error("invalid command argument: {}",e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_COMMAND_INPUT, null);
        } catch (InvalidPaymentException e) {
            log.error("invalid payment input: {}",e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_PAYMENT_INPUT, null);
        }catch (InvalidCardException e){
            log.error("invalid card: {}",e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_CARD,null);
        }
        catch (Exception e) {
            log.error("unexpected error: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR,null);
        }

    }

    @Override
    public Result requestPaymentVerification(RequestPaymentVerificationCommand command) {
        return null;
    }

    @Override
    public Result completePayment(CompletePaymentCommand command) {
        return null;
    }
}
