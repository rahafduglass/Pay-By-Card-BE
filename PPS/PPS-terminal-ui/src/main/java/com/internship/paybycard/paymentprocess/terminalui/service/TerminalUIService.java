package com.internship.paybycard.paymentprocess.terminalui.service;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.response.InitiatePaymentHandlerResponse;
import com.internship.paybycard.paymentprocess.terminalui.handler.PaymentInputHandler;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.*;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class TerminalUIService {
    private final PaymentProcessUseCase paymentProcessUseCase;

    public void start() {
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
                    initiatePaymentHandlerResponse = paymentInputHandler.initiatePayment();
                    continue;
                case 2:
                    if (isNull(initiatePaymentHandlerResponse)) {
                        System.out.println(RED + "payment hasn't been created yet, please initiate payment first" + RESET);
                        continue;
                    }
                    paymentInputHandler.verifyPayment(initiatePaymentHandlerResponse.getPaymentReferenceNumber());
                    continue;
                case 3:
                    if (isNull(initiatePaymentHandlerResponse)) {
                        System.out.println(RED + "payment hasn't been created yet, please initiate payment first" + RESET);
                        continue;
                    }
                    paymentInputHandler.completePayment(initiatePaymentHandlerResponse.getPaymentReferenceNumber(), initiatePaymentHandlerResponse.getCard());
                case 4:
                    System.out.println(ORANGE + " want to pay again?  " + RED + "n/" + GREEN + "y" + RESET);
                    String payAgain = scanner.nextLine();
                    if (payAgain.equals("y")) {
                        initiatePaymentHandlerResponse = null;
                        continue;
                    }
                    System.out.println("exiting, thank u for using paybycard, to access terminal UI again rerun the app XDD");
                    break outerLoop;
                default:
                    System.out.println(RED + "invalid option number: " + RESET);
            }
        }
    }
}