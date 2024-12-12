package org.rapi.rapi.presentation.configuration;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.rapi.rapi.application.auth.service.query.GetUserByNameQuery;
import org.rapi.rapi.application.auth.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final GetUserByNameQuery getUserByNameQuery;

    public JwtRequestFilter(JwtUtil jwtUtil, GetUserByNameQuery getUserByNameQuery) {
        this.jwtUtil = jwtUtil;
        this.getUserByNameQuery = getUserByNameQuery;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = getUserByNameQuery.getUserByName(username);

                if (jwtUtil.validateToken(jwt, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, null);
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid JWT Token");
                    return;
                }
            }
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                "JWT Token has expired");
            return;
        } catch (MalformedJwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Malformed JWT Token");
            return;
        } catch (SignatureException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                "Invalid JWT Signature");
            return;
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "An unexpected error occurred");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message)
        throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
    }
}
