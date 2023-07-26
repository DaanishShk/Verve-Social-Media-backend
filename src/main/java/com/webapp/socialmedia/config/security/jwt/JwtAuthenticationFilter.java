package com.webapp.socialmedia.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.socialmedia.config.exceptions.custom.ApiRequestException;
import com.webapp.socialmedia.config.exceptions.exception.ApiExceptionDetails;
import com.webapp.socialmedia.config.security.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper mapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            // try-catch skipped
            try {
                username = this.jwtUtil.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(mapper.writeValueAsString(new ApiExceptionDetails("JWT token expired",
                        HttpStatus.UNAUTHORIZED, LocalDateTime.now())));
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                return;
            }

            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) { // why is second value null?
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // what does the above class do?
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Token is not validated");
            }
        }

//        DO NOT KEEP THIS WITHIN IF STATEMENT
        filterChain.doFilter(request, response);
    }
}
