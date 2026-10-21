package com.fiap.steteless_jwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*Utiliza se o padrão do nome da classe UserService que o Spring Security reconhece */
@Service
public class UserService implements UserDetailsService {

    private final Map<String, String> users = new HashMap<>();

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        users.put("usuario", passwordEncoder.encode("senha123"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encodedPassword = users.get(username);
        if (encodedPassword == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return User.withUsername(username).password(encodedPassword).authorities(new ArrayList<>()).build();
    }

    public boolean validateUserCredentials(String username, String password) {
        String encodedPassword = users.get(username);
        return encodedPassword != null && passwordEncoder.matches(password, encodedPassword);
    }

}
