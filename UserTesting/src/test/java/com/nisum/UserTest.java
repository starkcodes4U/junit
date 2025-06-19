package com.nisum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testUserProperties() {
        User user = new User("Alice", "alice@example.com", 25);

        assertAll("user",
                () -> assertEquals("Alice", user.getName()),
                () -> assertEquals("alice@example.com", user.getEmail()),
                () -> assertEquals(25, user.getAge()),
                () -> assertNotNull(user.getName()),
                () -> assertTrue(user.getAge() > 18)
        );
    }

    @Test
    void testValidateAgeThrowsException() {
        UserService service = new UserService();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.validateAge(15);
        });

        assertEquals("Underage", exception.getMessage());
    }
}

