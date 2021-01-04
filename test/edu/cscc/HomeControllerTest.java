package edu.cscc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest extends MVCTest{

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    void index() {
        assertRouteExists("Home", "index", HomeController.class);
    }
}