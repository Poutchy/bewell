package com.example.authservice.controller;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    @Value("${google.clientId}")
    private String googleClientId;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User fullUser = userRepository.findByUsername(userDetails.getUsername());
        String token = jwtUtils.generateToken(userDetails.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", fullUser.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/client-signup")
    public String registerClient(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        User newUser = new User(
                null,
                user.getUsername(),
                encoder.encode(user.getPassword()),
                "client",
                false
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }

    @PostMapping("/salon-signup")
    public String registerSalon(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        User newUser = new User(
                null,
                user.getUsername(),
                encoder.encode(user.getPassword()),
                "salon",
                false
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/google-signin")
    public String authenticateWithGoogle(@RequestBody Map<String, String> body) {
        String idTokenString = body.get("credential");

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                    .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();

                // Try to find user or create new one
                User user = userRepository.findByUsername(email);
                if (user == null) {
                    user = new User(null, email, "", "client", true);
                    userRepository.save(user);
                }

                // Generate JWT
                String generatedToken = jwtUtils.generateToken(user.getUsername());
                return generatedToken;

            } else {
                throw new RuntimeException("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error verifying Google token: " + e.getMessage();
        }
    }

}
