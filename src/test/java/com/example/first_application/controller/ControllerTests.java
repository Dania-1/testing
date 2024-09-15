package com.example.first_application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controller.class)  // Ensure this matches the name of your controller class
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        // Initialization before each test
    }

    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.greeting").value("Hello, World"));

    }

    @Test
    public void testHelloEndpointWithNameParam() throws Exception {
        mockMvc.perform(get("/hello").param("name", "dania"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.greeting").value("Hello, dania"));
    }

    @Test
    public void testInfoEndpoint() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").exists())
                .andExpect(jsonPath("$.client_address").exists())
                .andExpect(jsonPath("$.host_name").exists())
                .andExpect(jsonPath("$.headers").exists());
    }
}
