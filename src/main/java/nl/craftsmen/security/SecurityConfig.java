package nl.craftsmen.security;

import nl.craftsmen.security.apikey.ApiKeyAuthenticationConverter;
import nl.craftsmen.security.apikey.ApiKeyAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * Inspired by https://github.com/gregwhitaker/springboot-webflux-apikey-example/tree/master/src/main/java/example/security.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	private static final String[] FILTER_IGNORE = {
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**",
			"/swagger-ui.html",
			"/webjars/**",
			// H2 Database Console
			"/h2-console/**",
			// Debug endpoints (local development only)
			"/debug/**",
			// Actuator public endpoints
			"/actuator/**"
	};

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
        ApiKeyAuthenticationManager apiKeyAuthenticationManager, ApiKeyAuthenticationConverter apiKeyAuthenticationConverter) {

		final var authenticationWebFilter = new AuthenticationWebFilter(apiKeyAuthenticationManager);
		authenticationWebFilter.setServerAuthenticationConverter(apiKeyAuthenticationConverter);

		// Set custom authentication failure handler to return 401 without WWW-Authenticate header
		authenticationWebFilter.setAuthenticationFailureHandler((webFilterExchange, exception) -> {
			webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return webFilterExchange.getExchange().getResponse().setComplete();
		});

		return http
				.exceptionHandling()
				.authenticationEntryPoint((exchange, ex) -> {
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					return Mono.fromRunnable(() -> exchange.getResponse().setComplete());
				})
				.and()
				.authorizeExchange()
				.pathMatchers(FILTER_IGNORE)
				.permitAll()
				.anyExchange()
				.authenticated()
				.and()
				.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.httpBasic()
				.disable()
				.csrf()
				.disable()
				.formLogin()
				.disable()
				.logout()
				.disable()
				.headers()
				.frameOptions()
				.disable()
				.and()
				.build();
	}
}
