package com.internship.paybycard.paymentprocess;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.service.TerminalUIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.*;

@SpringBootApplication
public class PaymentProcessSystem {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PaymentProcessSystem.class, args);

        System.out.println(ORANGE + "WANT TO START TERMINAL UI?" + RESET + RED + " n" + RESET + "/" + GREEN + "y" + RESET);

        while (true) {
            try {
                String option = new Scanner(System.in).nextLine();
                switch (option) {
                    case "y":
                        TerminalUIService terminalUiService = new TerminalUIService(ctx.getBean(PaymentProcessUseCase.class));
                        terminalUiService.start();
                        break;
                    case "n":
                        break;
                    default:
                        System.out.println(RED + "please pick a valid option" + RESET);
                }

            } catch (Exception e) {
                System.out.println(RED + "please pick a valid option" + RESET);
            }
        }
    }
}
