package com.example.library.repository.dao;

import com.example.library.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserRepository(@Lazy BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByEmail(String email) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode("12345")) //this should fetch from datasource.
                .firstName("first")
                .lastName("last")
                .build();
    }
}
