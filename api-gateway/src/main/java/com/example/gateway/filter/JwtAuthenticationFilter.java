package com.example.gateway.filter;

import com.example.gateway.constant.ApiGatewayConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtParser jwtParser;

    public JwtAuthenticationFilter(@Value("${jwt.secret}") String secret) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/api/user/login")) {
            return chain.filter(exchange);
        }

        List<String> auth = exchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
        if (auth.isEmpty() || !auth.getFirst().startsWith(ApiGatewayConstants.BEARER)) {
            return unauthorized(exchange);
        }

        String token = auth.getFirst().substring(7);
        try {
            Jws<Claims> claims = jwtParser.parseClaimsJws(token);
            String subject = claims.getBody().getSubject();
            String role = claims.getBody().get("role", String.class);

            ServerWebExchange mutated = exchange.mutate()
                    .request(r -> r.header(ApiGatewayConstants.X_USER_ID, subject)
                            .header(ApiGatewayConstants.X_USER_ROLE, role))
                    .build();

            return chain.filter(mutated);
        } catch (JwtException ex) {
            return unauthorized(exchange);
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange ex) {
        ex.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return ex.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
