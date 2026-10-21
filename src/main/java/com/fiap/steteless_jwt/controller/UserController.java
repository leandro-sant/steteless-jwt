package com.fiap.steteless_jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @GetMapping
    public String getAllUsers(){
        return "Esta rota é protegida, somente usuários autorizados";
    }

}
