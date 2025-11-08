package com.example.spring_boot_docker.controller;
import java.util.Map;

import java.util.Collections;
import com.example.spring_boot_docker.model.User;
import com.example.spring_boot_docker.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
      String token = authService.register(user.getUsername(), user.getEmail(), user.getPassword());
      if (token == null) {
        throw new RuntimeException("Invalid credentials");
      }
      else if (token.equals("UserExistsError") || token.equals("PasswordComplexityError") || token.equals("UsernameLengthError") || token.equals("UsernameFormatError") || token.equals("EmailFormatError")) {
        throw new RuntimeException(token);
      }
      return Collections.singletonMap("token", token);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
      String token = authService.authenticate(user.getUsername(), user.getPassword());
      if (token == null) {
        throw new RuntimeException("Invalid credentials");
      }
      return Collections.singletonMap("token", token);
    }
        
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/test")
    public String test() {
     return "OK";
    }
}
