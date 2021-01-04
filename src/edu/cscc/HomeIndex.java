package edu.cscc;

import edu.cscc.framework.ApplicationView;
import edu.cscc.framework.MVCContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HomeIndex extends ApplicationView {
    public HomeIndex(MVCContext context) {
        super(context);
    }

    @Override
    public void show() {
        System.out.println("Welcome to Lackluster Video!");
        System.out.println("1. Manage Orders");
        System.out.println("2. Exit");
        System.out.println("Choice:");
        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    route("Orders", "index");
                    break;
                case 2:
                    route("Home", "goodbye");
                    break;
            }
        } catch (InputMismatchException ex) {
            route("Home", "index");

        }

    }

}
