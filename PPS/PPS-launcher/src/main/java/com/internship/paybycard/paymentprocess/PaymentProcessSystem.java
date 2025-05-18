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

        TerminalUIRunner terminalUIRunner = ctx.getBean(TerminalUIRunner.class);
        Thread thread = new Thread(terminalUIRunner);
        thread.setDaemon(false);
        thread.start();
    }
}
