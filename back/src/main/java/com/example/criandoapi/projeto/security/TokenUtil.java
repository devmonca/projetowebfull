package com.example.criandoapi.projeto.security;

import com.example.criandoapi.projeto.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class TokenUtil {
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final long EXPIRATION = 12*60*60*1000;
    private static final String SECRET_KEY = "9MBkwdcdCNFWGMquS29MgoD2bXTMZ7nBr9oqdfDjHc0";
    private static final String EMISSOR = "DevMonca";

    // Criação do token
    public static String createToken(Usuario usuario){
        Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(usuario.getNome())
                .setIssuer(EMISSOR)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION)) // Data atual + expiração
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validação do token
    private static boolean isExpirationValid(Date expiration){
        return expiration.after(new Date(System.currentTimeMillis()));
    }

    private static boolean isEmissorValid(String issuer){
        return EMISSOR.equals(issuer);
    }

    private static boolean isSubjectValid(String username){
        return username != null && !username.isEmpty();
    }

    public static Authentication validate(HttpServletRequest request){
        // requisição aberta ou precisa de autenticação?
        String token = request.getHeader(HEADER); // retira o cabeçalho
//        token.substring(PREFIX,"");
//        token.substring("Bearer ".length());// retira o prefixo

        if (token == null || !token.startsWith(PREFIX)) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }
        token = token.replace(PREFIX, "").trim();

        // Abrindo a caixa
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token);

        String username = jwsClaims.getBody().getSubject();
        String issuer = jwsClaims.getBody().getIssuer();
        Date expire = jwsClaims.getBody().getExpiration();

        if (isSubjectValid(username) && isEmissorValid(issuer) && isExpirationValid(expire)) {
            return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
        }

        return null;
    }
}
