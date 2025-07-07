package ru.platform.config.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.platform.config.SecurityService;
import ru.platform.utils.JwtUtil;


import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final SecurityService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        final String requestUri = request.getRequestURI();
        log.debug("JwtFilter processing request to: {}", requestUri);

        if (isPublicEndpoint(request)) {
            log.debug("Skipping JWT check for public endpoint: {}", requestUri);
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header for: {}", requestUri);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        final String accessToken = authHeader.substring(7);
        try {
            log.debug("Validating JWT token for request: {}", requestUri);

            if (!jwtUtil.isTokenValid(accessToken)) {
                log.warn("Invalid JWT token for request: {}", requestUri);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return;
            }

            String username = jwtUtil.extractUsername(accessToken);
            if (username == null) {
                log.warn("Cannot extract username from JWT for request: {}", requestUri);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Cannot extract username from token");
                return;
            }

            log.debug("Loading user details for: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);

            log.debug("Authenticated user: {} for request: {}", username, requestUri);

        } catch (Exception e) {
            log.error("JWT authentication failed for request: " + requestUri, e);
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/")
                || path.startsWith("/api/chat/room")
                || path.startsWith("/ws")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/boosting-platform/")
                || path.startsWith("/actuator/")
                || path.startsWith("/api/games/")
                || path.startsWith("/api/offer/public/")
                || path.startsWith("/api/carousel/");
    }
}