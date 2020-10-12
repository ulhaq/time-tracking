package com.timetrack.mvp.auth;

import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    Logger log = LoggerFactory.getLogger(JwtUtils.class);

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        var roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).claim("rol", roles).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Date getExpiryFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            log.error(String.format("Request to parse expired JWT : %s failed : %s", authToken, e.getMessage()));
        } catch (UnsupportedJwtException e) {
            log.error(String.format("Request to parse unsupported JWT : %s failed : %s",authToken, e.getMessage()));
        } catch (MalformedJwtException e) {
            log.error(String.format("Request to parse invalid JWT : %s failed : %s", authToken, e.getMessage()));
        } catch (SignatureException e) {
            log.error(String.format("Request to parse JWT with invalid signature : %s failed : %s", authToken, e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.error(String.format("Request to parse empty or null JWT : %s failed : %s", authToken, e.getMessage()));
        }

        return false;
    }
}