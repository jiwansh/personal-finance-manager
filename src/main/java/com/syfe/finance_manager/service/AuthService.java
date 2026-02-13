package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.LoginRequest;
import com.syfe.finance_manager.exception.BadRequestException;
import com.syfe.finance_manager.exception.ConflictException;
import com.syfe.finance_manager.exception.UnauthorizedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.syfe.finance_manager.dto.RegisterRequest;
import com.syfe.finance_manager.entity.User;
import com.syfe.finance_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public User register(RegisterRequest request) {

        if(userRepository.existsByUsername(request.getUsername())){
            throw new ConflictException("User already exists with this email");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        return userRepository.save(user);
    }

    public User login(LoginRequest request){

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));


        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException("Invalid username or password");
        }

        return user;
    }

}
