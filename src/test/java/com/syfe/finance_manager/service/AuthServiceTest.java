package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.LoginRequest;
import com.syfe.finance_manager.dto.RegisterRequest;
import com.syfe.finance_manager.entity.User;
import com.syfe.finance_manager.exception.ConflictException;
import com.syfe.finance_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success(){

        RegisterRequest req = new RegisterRequest();
        req.setUsername("test@gmail.com");
        req.setPassword("123");
        req.setFullName("JK");
        req.setPhoneNumber("999");

        when(userRepository.existsByUsername("test@gmail.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User user = authService.register(req);

        assertEquals("test@gmail.com", user.getUsername());
    }

    @Test
    void register_duplicate(){

        RegisterRequest req = new RegisterRequest();
        req.setUsername("test@gmail.com");

        when(userRepository.existsByUsername("test@gmail.com")).thenReturn(true);

        assertThrows(ConflictException.class, () -> authService.register(req));
    }

    @Test
    void login_success(){

        LoginRequest req = new LoginRequest();
        req.setUsername("test@gmail.com");
        req.setPassword("123");

        User user = new User();
        user.setUsername("test@gmail.com");
        user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("123"));

        when(userRepository.findByUsername("test@gmail.com")).thenReturn(Optional.of(user));

        User result = authService.login(req);

        assertEquals("test@gmail.com", result.getUsername());
    }
}
