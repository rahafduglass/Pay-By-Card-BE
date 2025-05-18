package com.internship.paybycard.paymentprocess;

import com.internship.paybycard.paymentprocess.terminalui.service.TerminalUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.*;

@Component
public class TerminalUIRunner implements Runnable {
    private volatile boolean running = true;

    @Autowired
    private TerminalUIService terminalUIService;

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(GREEN + "terminal is running" + BLUE +
                "\ntype" + ORANGE + " \"start\" " + BLUE + "to start payment process " +
                "\ntype" + ORANGE + " \"exit\" " + BLUE + "to exit terminal UI"+RESET);
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                running = false;
                System.out.println("Shutting down terminal...");
            } else if ("start".equalsIgnoreCase(input)) {
                terminalUIService.start();
            }
        }
    }
}
