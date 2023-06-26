package com.example.bankingservice.configurations;

import com.example.bankingservice.domain.utils.CustomException;
import com.example.bankingservice.outbound.jpa.users.UserEntityRepo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private UserEntityRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (isEmpty(header) || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            // Get jwt token and validate
            final String token = header.split(" ")[1].trim();
            if (!jwtTokenUtil.validate(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = JwtTokenUtil.decodeToken(token);

            // Get user identity and set it on the spring security context
            UserDetails userDetails = userRepo.
                    findUserEntityByPhoneNumber(claims.getSubject())
                    .orElse(null);

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails,
                    userDetails == null ?
                            List.of() : userDetails.getAuthorities()
            );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (CustomException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid.jwt.token\"}");
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }


}
