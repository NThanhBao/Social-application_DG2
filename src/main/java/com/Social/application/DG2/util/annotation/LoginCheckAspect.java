package com.Social.application.DG2.util.annotation;

import com.Social.application.DG2.config.JwtTokenUtil;
import com.Social.application.DG2.service.UsersService;
import com.Social.application.DG2.util.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;


@Aspect
@Component
public class LoginCheckAspect {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsersService customUserDetailsService;

    @Before("@annotation(CheckLogin)")
    public ResponseEntity<?> checkLogin( JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Token not found or token does not exist.");
        }

        String token = authHeader.substring(7);
        String username = null;
        try {
            username = jwtTokenUtil.extractUsername(token);
            if (username == null) {
                throw new UnauthorizedException("Unable to extract username from token.");
            }
        } catch (Exception e) {
            throw new UnauthorizedException(" "+ e.getMessage());
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        try {
            if (!jwtTokenUtil.validateToken(token, userDetails)) {
                throw new UnauthorizedException("Authentication failed");
            }
        } catch (Exception e) {
            throw new UnauthorizedException("Authentication failed: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}


