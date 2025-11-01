
package com.example.spring_boot_docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@SpringBootApplication
public class SpringBootDockerApplication {

    private final JdbcTemplate jdbcTemplate;

    // Constructor must match class name
    public SpringBootDockerApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/hello")
    public String getHello() {
        String result = jdbcTemplate.queryForObject(
            "SELECT name FROM test_table LIMIT 1",
            String.class
        );
        return "Value from DB: " + result;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerApplication.class, args);
    }
}

