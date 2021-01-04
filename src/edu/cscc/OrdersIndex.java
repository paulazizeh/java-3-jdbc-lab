package edu.cscc;

import edu.cscc.framework.ApplicationView;
import edu.cscc.framework.MVCContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrdersIndex extends ApplicationView {
    public OrdersIndex(MVCContext context) {
        super(context);
    }

    @Override
    public void show() {
        System.out.println("Orders Management:");
        System.out.println("1. Create order");
        System.out.println("2. Delete all orders");
        System.out.println("3. Home");
        System.out.println("Choice:");
        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    route("Orders", "create");
                    break;
                case 2:
                    route("Orders", "delete");
                    break;
                case 3:
                    route("Home", "index");
                    break;
            }
        } catch (InputMismatchException ex) {
            route("Orders", "index");

        }

    }

}
