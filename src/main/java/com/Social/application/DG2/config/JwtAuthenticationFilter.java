package com.Social.application.DG2.config;

import com.Social.application.DG2.service.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsersService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenUtil.validateToken(jwt)) {
                String username  = jwtTokenUtil.extractUsername(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken
                            authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception ex) {
            logger.error("failed on set user authentication", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) &&  bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;

    }
}
