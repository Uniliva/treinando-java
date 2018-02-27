package br.com.devmedia.wsjwt.webservice.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TokenJWTUtil {
    private static KeyGenerator keyGenerator = new KeyGenerator();

    public static String gerarToken(String username, List<String> roles) {

        Key key = keyGenerator.generateKey();

        String jwtToken = Jwts.builder()
                //header
                .signWith(SignatureAlgorithm.HS256, key)
                .setHeaderParam("typ", "JWT")
                //preload
                .setSubject(username)
                .setIssuer("DevMedia")
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(60L)))
                .claim("roles", roles)
                .compact();
        System.out.println("Token gerado:: "+jwtToken);
        return jwtToken;
    }

    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from
                (localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}