package com.taskflow.security;


import com.taskflow.mapper.TaskMapper;
import com.taskflow.security.JwtAuthenticationFilter;
import com.taskflow.service.TaskService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private JwtAuthenticationFilter jwtFilter; // IMPORTANT

    @Test
    void testUnauthorizedAccess() throws Exception {

        // Make JwtFilter do nothing but still be present
        Mockito.doAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(Mockito.any(), Mockito.any(), Mockito.any());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isForbidden());
    }
}


