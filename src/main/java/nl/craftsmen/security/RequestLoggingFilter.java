package nl.craftsmen.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestLoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("========================================");
        log.info("Incoming Request: {} {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getPath());
        log.info("Headers: {}", exchange.getRequest().getHeaders().toSingleValueMap());
        log.info("========================================");

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> log.info("Request completed successfully"))
                .doOnError(error -> log.error("Request failed with error: {}", error.getMessage()));
    }
}

