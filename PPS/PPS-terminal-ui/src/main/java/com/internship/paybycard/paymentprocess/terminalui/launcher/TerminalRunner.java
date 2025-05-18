package com.internship.paybycard.paymentprocess.terminalui.launcher;

import com.internship.paybycard.paymentprocess.PaymentProcessSystem;
import com.internship.paybycard.paymentprocess.terminalui.service.TerminalUIService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TerminalRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(PaymentProcessSystem.class, args);
        TerminalUIService terminalUIService = ctx.getBean(TerminalUIService.class);
        terminalUIService.start();
    }
}
