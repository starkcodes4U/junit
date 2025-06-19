package com.nisum;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LifecycleTest {

    @BeforeAll
    void beforeAll() {
        System.out.println(" @BeforeAll - Executed once before all tests");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println(" @BeforeEach - Executed before each test");
    }

    @Test
    void testOne() {
        System.out.println(" @Test - Test One is running");
    }

    @Test
    void testTwo() {
        System.out.println(" @Test - Test Two is running");
    }

    @AfterEach
    void afterEach() {
        System.out.println(" @AfterEach - Executed after each test");
    }

    @AfterAll
    void afterAll() {
        System.out.println(" @AfterAll - Executed once after all tests");
    }
}

