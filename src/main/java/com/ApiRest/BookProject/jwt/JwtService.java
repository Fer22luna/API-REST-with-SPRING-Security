package com.ApiRest.BookProject.jwt;

import com.ApiRest.BookProject.persistence.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    private static final String SECRET_KEY = "asdlasndajnfanflasnff3435326454hetgwrgreegerherhy34535345346erherherher53";

    public String getToken(UserDetails user) {

        return getToken(new HashMap<>(), user);

    }

    public String getToken(Map<String, Object> extraClaim, UserDetails user) {

        return Jwts.builder()
                .setClaims(extraClaim)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }
    
    public Role getRoleFromToken(String token) {
    return getClaim(token, claims -> {
        // Supongamos que el rol se almacena directamente como un campo en el payload con la clave "role"
        return claims.get("role", Role.class);
    });
}

    boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    // Este metodo me devuelve el payload (carga util) de decodificar el token
    private Claims getAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    // El propósito de este método es proporcionar una forma genérica de obtener información 
    // específica de los claims del token
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpiration(String token) {

        return getClaim(token, Claims::getExpiration);

    }

    public boolean isTokenExpired(String token) {

        return getExpiration(token).before(new Date());
    }

}
