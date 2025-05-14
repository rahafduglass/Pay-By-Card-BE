package com.internship.paybycard.paymentprocess.domain.usecase;


import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidCardException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.CompletePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.VerifyPaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.domain.dto.payment.response.InitiatePaymentUseCaseResponseImpl;
import com.internship.paybycard.paymentprocess.domain.model.InitiatePaymentModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PaymentProcessUseCaseImplTest {

    @Mock
    private InitiatePaymentModelMapper initiatePaymentModelMapper;

    @Mock
    private VerifyPaymentModelMapper verifyPaymentModelMapper;

    @Mock
    private CompletePaymentModelMapper completePaymentModelMapper;

    @InjectMocks
    private PaymentProcessUseCaseImpl paymentProcessUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenValidCommand_whenCallInitiatePayment_thenResultShouldBeActWithDataAndSuccessErrorCode() {
        String referenceNumber=UUID.randomUUID().toString();
        Result<InitiatePaymentUseCaseResponseImpl> result=new Result<>(Status.ACT, ErrorCode.SUCCESS,
                new InitiatePaymentUseCaseResponseImpl(referenceNumber, "initiatedSuccessfully"));

        InitiatePaymentModel model = mock(InitiatePaymentModel.class);
        when(initiatePaymentModelMapper.commandToModel(any())).thenReturn(model);
        doNothing().when(model).validatePayment();
        doNothing().when(model).process();
        when(model.result()).thenReturn(new Result<>(Status.ACT, ErrorCode.SUCCESS,
                new InitiatePaymentUseCaseResponseImpl(referenceNumber, "initiatedSuccessfully")));
        InitiatePaymentCommand command=mock(InitiatePaymentCommand.class);
        when(command.toString()).thenReturn("woof");
        Result<InitiatePaymentUseCaseResponse> actualResult=paymentProcessUseCase.initiatePayment(command);
        assertEquals(actualResult,result);
    }


    @Test
    public void givenValidCommand_whenCallVerifyPayment_thenReturnAcceptedResult() {
        VerifyPaymentModel verifyPaymentModel = mock(VerifyPaymentModel.class);
        VerifyPaymentCommand command = mock(VerifyPaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(verifyPaymentModelMapper.commandToModel(any(VerifyPaymentCommand.class))).thenReturn(verifyPaymentModel);
        doNothing().when(verifyPaymentModel).verifyPayment();
        doNothing().when(verifyPaymentModel).sendOtp();
        Result<Void> useCaseResult = paymentProcessUseCase.verifyPayment(command);
        Result<Void> expectedResult = new Result<>(Status.ACT, ErrorCode.NULL, null);
        assertEquals(expectedResult, useCaseResult);
    }


    @Test
    public void givenValidCommand_whenCallCompletePayment_thenReturnAcceptedResult() {
        CompletePaymentModel completePaymentModel = mock(CompletePaymentModel.class);
        CompletePaymentCommand command = mock(CompletePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(completePaymentModelMapper.commandToModel(any(CompletePaymentCommand.class))).thenReturn(completePaymentModel);
        doNothing().when(completePaymentModel).verifyOTP();
        doNothing().when(completePaymentModel).pay();
        Result<Void> useCaseResult = paymentProcessUseCase.completePayment(command);
        Result<Void> expectedResult = new Result<>(Status.ACT, ErrorCode.NULL, null);
        assertEquals(expectedResult, useCaseResult);
    }

    @Test
    public void givenInvalidOtp_whenCallCompletePayment_thenShouldThrowInvalidOtpException() {
        CompletePaymentModel completePaymentModel = mock(CompletePaymentModel.class);
        CompletePaymentCommand command = mock(CompletePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(completePaymentModelMapper.commandToModel(any(CompletePaymentCommand.class))).thenReturn(completePaymentModel);
        doThrow(new InvalidCardException("error", ErrorCode.INVALID_OR_EXPIRED_OTP)).when(completePaymentModel).verifyOTP();
        Result<Void> useCaseResult = paymentProcessUseCase.completePayment(command);
        Result<Void> expectedResult = new Result<>(Status.RJC, ErrorCode.INVALID_OR_EXPIRED_OTP, null);
        assertEquals(expectedResult, useCaseResult);
    }

    @Test
    public void givenInvalidReferenceNumber_whenCallCompletePayment_thenShouldThrowPaymentNotFoundException() {
        CompletePaymentModel completePaymentModel = mock(CompletePaymentModel.class);
        CompletePaymentCommand command = mock(CompletePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(completePaymentModelMapper.commandToModel(any(CompletePaymentCommand.class))).thenReturn(completePaymentModel);
        doThrow(new PaymentNotFoundException("error", ErrorCode.PAYMENT_NOT_FOUND)).when(completePaymentModel).verifyOTP();
        Result<Void> useCaseResult = paymentProcessUseCase.completePayment(command);
        Result<Void> expectedResult = new Result<>(Status.RJC, ErrorCode.PAYMENT_NOT_FOUND, null);
        assertEquals(expectedResult, useCaseResult);
    }

}
