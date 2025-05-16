package com.internship.paybycard;

import java.util.Scanner;


public class TerminalUILauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InitiatePaymentInputHandler handler = new InitiatePaymentInputHandler( scanner);
        outerLoop:
        while (true) {
            System.out.println("1. initiate payment");
            System.out.println("2. verify payment");
            System.out.println("3. complete payment");
            System.out.println("4. exit");
            System.out.println("pick an option number: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    handler.initiatePayment();
                    continue;
                case 2: {
                    //call verify payment use case then inform user accordingly
                    continue;
                }
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

