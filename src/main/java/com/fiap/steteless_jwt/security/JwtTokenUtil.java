package com.fiap.steteless_jwt.security;

import com.fiap.steteless_jwt.exeption.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    //criação a chave secreta de criptografia
    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final int jwtExpirationMs = 20000;

    //vai gerar o token com os atributos
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(jwtSecretKey)//assina o token com a chave segura
                .compact();
    }

    //pegar o user pelo token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //valida o token e valida se a chave está correta
    public boolean validateToken(String token) {
        try {
            //no parse dos claims é validado a expiração do token
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException("Token expirado. Efetue login novamente");
        } catch (Exception e) {
            return false;
        }
    }

}
