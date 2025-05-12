package com.priyakdey.droplet.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("DropletApiApplicationTest")
class DropletApiApplicationTest {

    @Test
    @DisplayName("context should load")
    void contextLoads() {
        // This test will automatically fail if the application context cannot be loaded
        assertTrue(true);
    }

}