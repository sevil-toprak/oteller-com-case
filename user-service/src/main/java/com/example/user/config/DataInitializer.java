package com.example.user.config;

import com.example.user.entity.User;
import com.example.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .userName("John@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .build());

            userRepository.save(User.builder()
                    .userName("Alex@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .build());
        }
    }
}