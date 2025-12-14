package com.catalogx.apigateway;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        if (!request.email().equals("testuser@example.com")
                || !request.password().equals("yourpassword")) {
            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(1L, request.email(), "USER");

        return Map.of("token", token);
    }
}
