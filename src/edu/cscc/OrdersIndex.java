package edu.cscc;

import edu.cscc.framework.ApplicationView;
import edu.cscc.framework.MVCContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrdersIndex extends ApplicationView {
    private ResultSet resultSet;
    public OrdersIndex(MVCContext context, ResultSet resultSet) {
        super(context);
        this.resultSet = resultSet;
        }

    @Override
    public void show() {
        try {
            boolean isEmpty = true;
            while (resultSet.next()) {
                isEmpty = false;
                System.out.println();
                System.out.println("Order:");
                System.out.println("************");
                System.out.println("Order ID: " + resultSet.getString(1));
                System.out.println("Employee Name: " + resultSet.getString(2) + " " + resultSet.getString(3));
                System.out.println("Customer Smart ID: " + resultSet.getString(4));
                System.out.println("Customer Name: " + resultSet.getString(5) + " " + resultSet.getString(6));
                System.out.println("Store Number: " + resultSet.getString(7));
                System.out.println("Rental Name: " + resultSet.getString(8));
            }
            if(isEmpty) {
                System.out.println("No orders found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println();
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
