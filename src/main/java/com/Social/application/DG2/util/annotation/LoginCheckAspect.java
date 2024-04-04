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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


import jakarta.servlet.http.HttpServletRequest;


@Aspect
@Component
public class LoginCheckAspect {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsersService customUserDetailsService;

    @Before("@annotation(CheckLogin)")
    public ResponseEntity<?> checkLogin(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Không tìm thấy token hoặc token không tồn tại.");
        }

        String token = authHeader.substring(7);
        String username = jwtTokenUtil.extractUsername(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        try {
            if (!jwtTokenUtil.validateToken(token, userDetails)) {
                throw new UnauthorizedException("Xác thực không thành công");
            }
        } catch (UnauthorizedException e) {
            throw e;
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token đã hết hạn");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Token không hợp lệ");
        } catch (Exception e) {
            throw new UnauthorizedException("Xác thực không thành công: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}


