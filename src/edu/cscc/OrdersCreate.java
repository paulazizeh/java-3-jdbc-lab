package edu.cscc;

import edu.cscc.framework.ApplicationView;
import edu.cscc.framework.MVCContext;

import java.util.*;

public class OrdersCreate extends ApplicationView {

    /**
     * Sets the context and configures it to exit by default
     * unless a route is specified.
     *
     * @param context The {@link MVCContext}.
     */
    public OrdersCreate(MVCContext context) {
        super(context);
    }

    @Override
    public void show() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> params = new HashMap<>();
        System.out.println("Create Order:");
        System.out.println("Employee id: ");
        String employeeId = scanner.nextLine();
        params.put("employeeId", employeeId);
        System.out.println("Customer id: ");
        String customerid = scanner.nextLine();
        params.put("customerid", customerid);
        int choice = 0;
        List<String> rentalIds = new ArrayList<>();
        do {


            System.out.println("1. Add a rental");
            System.out.println("2. Complete order");
            System.out.println("3. Manage orders");
            System.out.println("Choice:");

            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        System.out.println("Rental id: ");
                        String rentalId = scanner.nextLine();
                        rentalIds.add(rentalId);

                        break;
                    case 2:
                        params.put("rentalIds", rentalIds);
                        route("Orders", "complete", params);
                        break;
                    case 3:
                        route("Orders", "index");
                        break;
                }
            } catch (InputMismatchException ex) {
                route("Orders", "index");

            }
        } while (choice == 1);
    }
}
