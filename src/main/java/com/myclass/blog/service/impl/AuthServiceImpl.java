package com.myclass.blog.service.impl;

import com.myclass.blog.entity.Role;
import com.myclass.blog.entity.User;
import com.myclass.blog.exception.BlogApiException;
import com.myclass.blog.payload.LoginDto;
import com.myclass.blog.payload.RegisterDto;
import com.myclass.blog.repository.RoleRepository;
import com.myclass.blog.repository.UserRepository;
import com.myclass.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User login successfully!";
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Username is Already exists!");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Email is Already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role useRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(useRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully!";
    }


}
