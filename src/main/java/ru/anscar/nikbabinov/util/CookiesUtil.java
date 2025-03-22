package ru.anscar.nikbabinov.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookiesUtil {

    public String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    public Cookie setTokenInCookies(String token){
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO set true in production for HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(3600); // time life 1 hour
        cookie.setComment("SameSite=Lax");
        return cookie;
    }
}
