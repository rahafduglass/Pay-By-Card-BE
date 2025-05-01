package com.internship.paybycard.paymentprocess.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.RequestPaymentVerificationCommand;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidPaymentException;
import com.internship.paybycard.paymentprocess.core.domain.mapper.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentProcessUseCaseImpl implements PaymentProcessUseCase {

    private final InitiatePaymentModelMapper initiatePaymentModelMapper;


    @Override
    public Result initiatePayment(InitiatePaymentCommand command) {
        try {
            InitiatePaymentModel initiatePaymentModel = initiatePaymentModelMapper.commandToModel(command);
            initiatePaymentModel.validatePayment();
            initiatePaymentModel.initiate();
        } catch (IllegalArgumentException e) {
            return new Result(Status.RJC, ErrorCode.INVALID_COMMAND_INPUT);
        }catch (InvalidPaymentException e){
            return new Result(Status.RJC, ErrorCode.INVALID_PAYMENT_INPUT);
        }catch(Exception e){
            return new Result(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return new Result(Status.ACT, ErrorCode.NULL);
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
