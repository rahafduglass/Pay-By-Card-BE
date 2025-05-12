package com.internship.paybycard.paymentprocess.domain.usecase;


import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.exception.InsufficientCardBalance;
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
import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    public void givenAmountBiggerThanBalance_whenCallInitiatePayment_thenShouldThrowInsufficientCardBalance(){
        InitiatePaymentModelImpl initiatePaymentModel = mock(InitiatePaymentModelImpl.class);
        when(initiatePaymentModel.validatePayment()).thenThrow(new InvalidCardException("error",ErrorCode.INSUFFICIENT_CARD_BALANCE));
        InitiatePaymentCommand command= mock(InitiatePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(initiatePaymentModelMapper.commandToModel(any(InitiatePaymentCommand.class))).thenReturn(initiatePaymentModel);
        Result<InitiatePaymentUseCaseResponse> useCaseResult=paymentProcessUseCase.initiatePayment(command);
        Result<InitiatePaymentUseCaseResponse> expectedResult= new Result<>(Status.RJC, ErrorCode.INSUFFICIENT_CARD_BALANCE,null);
        assertEquals(expectedResult,useCaseResult);
    }

    @Test
    public void givenValidCommand_whenCallInitiatePayment_thenReturnAcceptedResultWithReferenceNumber(){
        InitiatePaymentModel initiatePaymentModel = mock(InitiatePaymentModel.class);
        InitiatePaymentCommand command= mock(InitiatePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(initiatePaymentModelMapper.commandToModel(any(InitiatePaymentCommand.class))).thenReturn(initiatePaymentModel);
        when(initiatePaymentModel.validatePayment()).thenReturn(true);
        RealPaymentDto realPaymentDto = new RealPaymentDto();
        realPaymentDto.setReferenceNumber(UUID.randomUUID().toString());
        when(initiatePaymentModel.process()).thenReturn(realPaymentDto);
        Result<InitiatePaymentUseCaseResponse> useCaseResult=paymentProcessUseCase.initiatePayment(command);
        Result<InitiatePaymentUseCaseResponse> expectedResult= new Result<>(Status.ACT, ErrorCode.NULL, new InitiatePaymentUseCaseResponseImpl(realPaymentDto.getReferenceNumber(),"initiated successfully"));
        assertEquals(expectedResult,useCaseResult);
    }

    @Test
    public void givenValidCommand_whenCallVerifyPayment_thenReturnAcceptedResult(){
        VerifyPaymentModel verifyPaymentModel= mock(VerifyPaymentModel.class);
        VerifyPaymentCommand command= mock(VerifyPaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(verifyPaymentModelMapper.commandToModel(any(VerifyPaymentCommand.class))).thenReturn(verifyPaymentModel);
        doNothing().when(verifyPaymentModel).verifyPayment();
        doNothing().when(verifyPaymentModel).sendOtp();
        Result<Void> useCaseResult=paymentProcessUseCase.verifyPayment(command);
        Result<Void> expectedResult= new Result<>(Status.ACT,ErrorCode.NULL,null);
        assertEquals(expectedResult,useCaseResult);
    }

    @Test
    public void givenInvalidReferenceNumber_whenCallVerifyPayment_thenShouldThrowPaymentNotFoundException(){
        VerifyPaymentModel verifyPaymentModel= mock(VerifyPaymentModel.class);
        VerifyPaymentCommand command= mock(VerifyPaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(verifyPaymentModelMapper.commandToModel(any(VerifyPaymentCommand.class))).thenReturn(verifyPaymentModel);
        doThrow(new PaymentNotFoundException("error",ErrorCode.PAYMENT_NOT_FOUND)).when(verifyPaymentModel).verifyPayment();
        Result<Void> useCaseResult=paymentProcessUseCase.verifyPayment(command);
        Result<Void> expectedResult= new Result<>(Status.RJC, ErrorCode.PAYMENT_NOT_FOUND,null);
        assertEquals(expectedResult,useCaseResult);
    }

    @Test
    public void givenValidCommand_whenCallCompletePayment_thenReturnAcceptedResult(){
        CompletePaymentModel completePaymentModel= mock(CompletePaymentModel.class);
        CompletePaymentCommand command= mock(CompletePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(completePaymentModelMapper.commandToModel(any(CompletePaymentCommand.class))).thenReturn(completePaymentModel);
        doNothing().when(completePaymentModel).verifyOTP();
        doNothing().when(completePaymentModel).pay();
        Result<Void> useCaseResult=paymentProcessUseCase.completePayment(command);
        Result<Void> expectedResult= new Result<>(Status.ACT, ErrorCode.NULL,null);
        assertEquals(expectedResult,useCaseResult);
    }

    @Test
    public void givenInvalidOtp_whenCallCompletePayment_thenShouldThrowInvalidOtpException(){
        CompletePaymentModel completePaymentModel= mock(CompletePaymentModel.class);
        CompletePaymentCommand command= mock(CompletePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(completePaymentModelMapper.commandToModel(any(CompletePaymentCommand.class))).thenReturn(completePaymentModel);
        doThrow(new InvalidCardException("error",ErrorCode.INVALID_OR_EXPIRED_OTP)).when(completePaymentModel).verifyOTP();
        Result<Void> useCaseResult=paymentProcessUseCase.completePayment(command);
        Result<Void> expectedResult= new Result<>(Status.RJC, ErrorCode.INVALID_OR_EXPIRED_OTP,null);
        assertEquals(expectedResult,useCaseResult);
    }

    @Test
    public void givenInvalidReferenceNumber_whenCallCompletePayment_thenShouldThrowPaymentNotFoundException(){
        CompletePaymentModel completePaymentModel= mock(CompletePaymentModel.class);
        CompletePaymentCommand command= mock(CompletePaymentCommand.class);
        when(command.toString()).thenReturn("meow");
        when(completePaymentModelMapper.commandToModel(any(CompletePaymentCommand.class))).thenReturn(completePaymentModel);
        doThrow(new PaymentNotFoundException("error",ErrorCode.PAYMENT_NOT_FOUND)).when(completePaymentModel).verifyOTP();
        Result<Void> useCaseResult=paymentProcessUseCase.completePayment(command);
        Result<Void> expectedResult= new Result<>(Status.RJC, ErrorCode.PAYMENT_NOT_FOUND,null);
        assertEquals(expectedResult,useCaseResult);
    }

}
