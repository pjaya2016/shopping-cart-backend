package com.jay.cvproject.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthUtil {


    public Claims validateToken(String jwtToken) {
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey(Constants.key)
                .build()
                .parseClaimsJws(jwtToken);
        return jws.getBody();
    }

    public boolean checkJWTToken(String authenticationHeader) {
        return authenticationHeader != null && authenticationHeader.startsWith(Constants.PREFIX);
    }

    public void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public String getToken(String email) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        //or HS384 or HS512
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(Constants.key)
                .compact();

        return "Bearer " + token;
    }
}
