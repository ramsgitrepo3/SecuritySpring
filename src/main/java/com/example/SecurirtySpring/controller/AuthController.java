package com.example.SecurirtySpring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.SecurirtySpring.Dto.LoginRequest;
import com.example.SecurirtySpring.Dto.RegisterRequest;
import com.example.SecurirtySpring.entity.AppUser;

import com.example.SecurirtySpring.entity.Role;
import com.example.SecurirtySpring.repository.RoleRepository;
import com.example.SecurirtySpring.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        AppUser u = new AppUser();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        if (req.getRoles() != null && !req.getRoles().isEmpty()) {
            roles = req.getRoles().stream()
                     .map(rName -> roleRepo.findByName(rName)
                             .orElseGet(() -> roleRepo.save(new Role(null, rName))))
                     .collect(Collectors.toSet());
        } else {
            // default role
            Role userRole = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));
            roles.add(userRole);
        }
        u.setRoles(roles);
        userRepo.save(u);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            var roles = auth.getAuthorities().stream()
                           .map(Object::toString)
                           .collect(Collectors.toList());

            return ResponseEntity.ok(
                    java.util.Map.of(
                        "username", auth.getName(),
                        "roles", roles,
                        "message", "Login successful"
                    )
            );
        } catch (org.springframework.security.core.AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
