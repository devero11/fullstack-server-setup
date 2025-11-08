package com.example.spring_boot_docker.service;

import com.example.spring_boot_docker.model.User;
import com.example.spring_boot_docker.repository.UserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

    public static final String SECRET = "my-super-secret-key-that-is-at-least-32-bytes!";
    public static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION = 1000 * 60 * 60 * 60; // 24 hours



    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String username, String email, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        // Check if raw password meets basic criteria (8 characters, lower and upper case letters, digits, special symbols)
        if (rawPassword.length() < 8 ||
            !rawPassword.matches(".*[a-z].*") ||
            !rawPassword.matches(".*[A-Z].*") ||
            !rawPassword.matches(".*\\d.*") ||
            !rawPassword.matches(".*[!@#$%^&*()].*")) {
            return "PasswordComplexityError";
        }

        // Check if username is equal or longer than 4 characters
        if (username.length() < 4) {
            return "UsernameLengthError";
        }

        // Check if username or email already exists
        if (userRepository.findByUsername(username) != null ||
            userRepository.findByEmail(email) != null) {
            return "UserExistsError";
        }

        // Hash the password with bcrypt (automatically salted)
        String hashed = BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
        user.setPassword(hashed);
        userRepository.save(user);
        
        return authenticate(username, rawPassword);
    }

    public String authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;

        // Verify password
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), user.getPassword());
        if (!result.verified) return null;

    // Generate JWT
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }
}

