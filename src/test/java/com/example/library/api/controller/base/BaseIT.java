package com.example.library.api.controller.base;

import com.example.library.auth.JwtUtil;
import com.example.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    protected HttpHeaders getAuthentication() {
        final var headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION.toString(), getAccessToken());
        return headers;
    }

    private String getAccessToken() {
        final var user = User.builder()
                .email("test")
                .password("")
                .firstName("")
                .lastName("")
                .build();
        return "Bearer " + jwtUtil.createToken(user);
    }

}
