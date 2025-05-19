package com.internship.paybycard.paymentprocess;

import com.internship.paybycard.paymentprocess.terminalui.runner.TerminalUIRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TerminalUILauncher {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TerminalUILauncher.class, args);

        TerminalUIRunner terminalUIRunner = ctx.getBean(TerminalUIRunner.class);
        Thread thread = new Thread(terminalUIRunner);
        thread.setDaemon(false);
        thread.start();
    }
}
