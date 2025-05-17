package com.internship.paybycard.paymentprocess;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.launcher.TerminalUILauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.*;

@SpringBootApplication
public class PaymentProcessSystem {

    public static void main(String[] args)  {
        ApplicationContext ctx=SpringApplication.run(PaymentProcessSystem.class, args);

        System.out.println(ORANGE+"WANT TO START TERMINAL UI?"+RESET+RED+" n"+RESET+"/"+GREEN+"y"+RESET);
        String option=new Scanner(System.in).nextLine();
        if (option.equals("y")) {
            TerminalUILauncher.launch(ctx.getBean(PaymentProcessUseCase.class));
        }
    }
}
