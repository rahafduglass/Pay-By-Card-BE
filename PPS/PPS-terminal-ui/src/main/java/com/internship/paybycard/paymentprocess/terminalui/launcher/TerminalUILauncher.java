package com.internship.paybycard.paymentprocess.terminalui.launcher;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.payment.handler.PaymentHandler;

import java.io.IOException;
import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.ORANGE;
import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.RESET;

public class TerminalUILauncher {

    public static void launch(PaymentProcessUseCase paymentProcessUseCase) throws IOException {
        Scanner scanner = new Scanner(System.in);
        PaymentHandler handler = new PaymentHandler(scanner, paymentProcessUseCase);
        String paymentReferenceNumber = null;
        outerLoop:
        while (true) {
            System.out.println("1. initiate payment");
            System.out.println("2. verify payment");
            System.out.println("3. complete payment");
            System.out.println("4. exit");
            System.out.println(ORANGE+"pick an option number: "+RESET);
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    paymentReferenceNumber = handler.initiatePayment();
                    continue;
                case 2:
                    //call verify payment use case then inform user accordingly
                    continue;
                case 3:
                    System.out.println("enter otp code: ");
                    String otpCode = scanner.nextLine();
                    //call complete payment use case :D
                case 4:
                    System.out.println("exiting");
                    break outerLoop;
            }
        }
    }
}