package edu.cscc;

import edu.cscc.MVCTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdersControllerTest extends MVCTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void itCanRouteToOrdersIndex() {
        assertRouteExists("Orders", "index", OrdersController.class);
    }

    @Test
    public void itRendersTheOrdersIndexView() {
        routeRequest("Orders", "index");
        assertViewRendered(OrdersIndex.class);
    }

    @Test
    void delete() {
        assertRouteExists("Orders", "delete", OrdersController.class);
    }

    @Test
    void create() {
        assertRouteExists("Orders", "create", OrdersController.class);
    }

    @Test
    void save() {
        assertRouteExists("Orders", "save", OrdersController.class);
    }

    @Test
    void complete() {
        assertRouteExists("Orders", "complete", OrdersController.class);
    }
}