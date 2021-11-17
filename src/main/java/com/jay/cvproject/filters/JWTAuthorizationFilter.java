package com.jay.cvproject.filters;

import com.jay.cvproject.utilities.AuthUtil;
import com.jay.cvproject.utilities.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;

    public JWTAuthorizationFilter() {
        this.authUtil = new AuthUtil();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String header = request.getHeader(Constants.HEADER);
            if (authUtil.checkJWTToken(header)) {
                Claims claims = authUtil.validateToken(header.replace(Constants.PREFIX, ""));
                if (claims.get("authorities") != null) {
                    authUtil.setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

}
