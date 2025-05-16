package com.internship.paybycard.paymentprocess.domain.usecase;


import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode.SUCCESS;
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
    public void testCallingInitiatePaymentModelMethods_inTheRightOrder_whenCallInitiatePayment() {
        InitiatePaymentCommand command = mock(InitiatePaymentCommand.class);
        InitiatePaymentModel model = mock(InitiatePaymentModel.class);
        Result<InitiatePaymentUseCaseResponse> expectedResult = new Result<>(Status.ACT, ErrorCode.SUCCESS, null);
        when(command.toString()).thenReturn("command log mock");
        when(initiatePaymentModelMapper.commandToModel(any(InitiatePaymentCommand.class))).thenReturn(model);
        when(model.result()).thenReturn(expectedResult);

        Result<InitiatePaymentUseCaseResponse> actualResult = paymentProcessUseCase.initiatePayment(command);

        InOrder inOrder = inOrder(model);
        inOrder.verify(model).validatePayment();
        inOrder.verify(model).process();
        inOrder.verify(model).result();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCallingVerifyPaymentModelMethods_inTheRightOrder_whenCallVerifyPayment() {
        VerifyPaymentCommand command = mock(VerifyPaymentCommand.class);
        VerifyPaymentModel model = mock(VerifyPaymentModel.class);
        when(command.toString()).thenReturn("command log mock");
        when(verifyPaymentModelMapper.commandToModel(any(VerifyPaymentCommand.class))).thenReturn(model);
        Result<Void> expectedResult= new Result<>(Status.ACT, ErrorCode.SUCCESS, null);
        when(model.result()).thenReturn(expectedResult);

        Result<Void> actualResult = paymentProcessUseCase.verifyPayment(command);

        InOrder inOrder = inOrder(model);
        inOrder.verify(model).verifyPayment();
        inOrder.verify(model).sendOtp();
        inOrder.verify(model).result();
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCallingCompletePaymentModelMethods_inTheRightOrder_whenCallCompletePayment() {
        CompletePaymentCommand command = mock(CompletePaymentCommand.class);
        CompletePaymentModel model = mock(CompletePaymentModel.class);
        when(command.toString()).thenReturn("command log mock");
        when(completePaymentModelMapper.commandToModel(any(CompletePaymentCommand.class))).thenReturn(model);
        Result<Void> expectedResult = new Result<>(Status.ACT, ErrorCode.SUCCESS, null);
        when(model.result()).thenReturn(expectedResult);

        Result<Void> actualResult = paymentProcessUseCase.completePayment(command);

        InOrder inOrder = inOrder(model);
        inOrder.verify(model).verifyOTP();
        inOrder.verify(model).pay();
        inOrder.verify(model).result();
        assertEquals(expectedResult, actualResult);
    }

}