package com.internship.paybycard.paymentprocess.terminalui.launcher;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.response.InitiatePaymentHandlerResponse;
import com.internship.paybycard.paymentprocess.terminalui.handler.PaymentInputHandler;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;

import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.ORANGE;
import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.RESET;

public class TerminalUILauncher {

    public static void launch(PaymentProcessUseCase paymentProcessUseCase) {
        Scanner scanner = new Scanner(System.in);
        ConsoleHandler consoleHandler = new ConsoleHandler(scanner);
        PaymentInputHandler paymentInputHandler = new PaymentInputHandler(consoleHandler, paymentProcessUseCase);
        InitiatePaymentHandlerResponse initiatePaymentHandlerResponse = null;
        outerLoop:
        while (true) {
            System.out.println("1. initiate payment");
            System.out.println("2. verify payment");
            System.out.println("3. complete payment");
            System.out.println("4. exit");
            System.out.println(ORANGE + "pick an option number: " + RESET);
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                     initiatePaymentHandlerResponse= paymentInputHandler.initiatePayment();
                    continue;
                case 2:
                    paymentInputHandler.verifyPayment(initiatePaymentHandlerResponse.getPaymentReferenceNumber());
                    continue;
                case 3:
                    paymentInputHandler.completePayment(initiatePaymentHandlerResponse.getPaymentReferenceNumber(),initiatePaymentHandlerResponse.getCard());
                    //call complete payment use case :D
                case 4:
                    System.out.println("exiting");
                    break outerLoop;
            }
        }
    }
}