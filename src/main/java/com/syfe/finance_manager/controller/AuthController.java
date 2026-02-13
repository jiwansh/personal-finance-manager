package com.syfe.finance_manager.controller;

import com.syfe.finance_manager.dto.RegisterRequest;
import com.syfe.finance_manager.dto.RegisterResponse;
import com.syfe.finance_manager.entity.User;
import com.syfe.finance_manager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import com.syfe.finance_manager.dto.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.syfe.finance_manager.config.CustomUserDetails;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){

        User user = authService.register(request);

        return ResponseEntity.status(201)
                .body(new RegisterResponse("User registered successfully", user.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session){

        User user = authService.login(request);

        session.setAttribute("userId", user.getId());

        CustomUserDetails userDetails = new CustomUserDetails(user.getUsername());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return ResponseEntity.ok("Login successful");
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){

        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }


}
