package com.catalogx.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    private JwtUtil util;

    public JwtAuthFilter(){
        super();
    }

    public static class Config{}

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            // 1. Check for bypass path (login)
            if(path.startsWith("/auth/login"))
            {
                return chain.filter(exchange);
            }

            // 2. Extract Authorization header
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer "))
            {
                return unauthorized(exchange);
            }

            try{
                String token = authHeader.substring(7);
                // 3. Validate token signature and expiry using the shared utility
                util.validateToken(token);

                exchange = exchange.mutate()
                        .request(
                                exchange.getRequest()
                                        .mutate()
                                        .header("X-GATEWAY-AUTH", "catalogx-internal")
                                        .build()
                        )
                        .build();
            }
            // 4. Catch validation failures and log them (THE FIX!)
            catch(RuntimeException e)
            {
                System.err.println("--- JWT VALIDATION FAILED AT API GATEWAY ---");
                System.err.println("Path: " + path);
                System.err.println("Exception: " + e.getClass().getSimpleName());
                System.err.println("Message: " + e.getMessage());
                e.printStackTrace(); // THIS WILL SHOW SignatureException or ExpiredJwtException
                System.err.println("---------------------------------------------");

                return unauthorized(exchange); // Return 401
            }

            // 5. Success: continue the filter chain to routing
            return chain.filter(exchange);
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange)
    {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}