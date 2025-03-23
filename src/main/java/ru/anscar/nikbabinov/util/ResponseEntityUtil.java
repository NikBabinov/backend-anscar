package ru.anscar.nikbabinov.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.security.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseEntityUtil {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookiesUtil cookiesUtil;

    public ResponseEntityUtil(JwtTokenProvider jwtTokenProvider, CookiesUtil cookiesUtil) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookiesUtil = cookiesUtil;
    }

    public ResponseEntity<Map<String, Object>> getMapResponseEntity(Users users, HttpServletResponse httpResponse) {
        if (users != null) {
            String role = users.getRoles().getRole();
            String token = jwtTokenProvider.createToken(users.getEmail(), role);

            Cookie cookie = cookiesUtil.setTokenInCookies(token);
            httpResponse.addCookie(cookie);

            UserDTO responseUser = new UserDTO(users.getId()
                    , users.getName()
                    , users.getEmail()
                    , null
                    , role);

            Map<String, Object> response = new HashMap<>();


            response.put("user", responseUser);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
