package com.fiap.steteless_jwt.controller;
import com.fiap.steteless_jwt.security.JwtTokenUtil;
import com.fiap.steteless_jwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthController(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    //vai validar o usuário e gerar o token JWT
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam String username,
                                              @RequestParam String password) {
        if (userService.validateUserCredentials(username, password)) {
            String token = jwtTokenUtil.generateToken(username);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }
    }
}