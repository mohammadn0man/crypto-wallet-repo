package com.social.Social.filter;

import com.social.Social.service.JwtService;
import com.social.Social.service.TokenService;
import com.social.Social.service.UserService;
import com.social.Social.util.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenService tokenService;

    /***
     * Filter to check if token used is expired or not
     * <p>if the token is expired the request is block and expired token response is passed</p>
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwt);
                if (tokenService.isExist(jwt)) {
                    throw new ExpiredJwtException(new DefaultHeader(), new DefaultClaims(), "Expired Token");
                }
            } catch (ExpiredJwtException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("{ ");
                sb.append("\"error\": \"Unauthorized\",\n");
                sb.append("\"message\": \"TOKEN_EXPIRED\",\n");
                sb.append("\"path\": \"").append(request.getRequestURL()).append("\"");
                sb.append("} ");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(sb.toString());
                return;
            }
        }


        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (Boolean.TRUE.equals(jwtService.validateToken(jwt, userDetails))) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
