package ru.anscar.nikbabinov.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import ru.anscar.nikbabinov.constants.RoleValue;
import ru.anscar.nikbabinov.util.CookiesUtil;

import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final CookiesUtil cookiesUtil;
    private SecretKey secretKey;

    @Value("${jwt.secret}")
    private String secret;

    public JwtTokenProvider(UserDetailsService userDetailsService, CookiesUtil cookiesUtil) {
        this.userDetailsService = userDetailsService;
        this.cookiesUtil = cookiesUtil;
    }

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String createToken(String username, String role) {
        Date now = new Date();
        long validityInMilliseconds = 3600000; // 1 hour
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", role)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return cookiesUtil.getTokenFromCookies(req);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
