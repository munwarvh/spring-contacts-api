package nl.craftsmen.security.apikey;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Checks the incoming {@link Authentication} and verifies that the request should be allowed.
 */
@Component
@AllArgsConstructor
@Slf4j
public class ApiKeyAuthenticationManager implements ReactiveAuthenticationManager {

	private final ApiKeyValidator apiKeyValidator;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		log.debug("Authenticating request with API key");
		return Mono.fromSupplier(() -> doAuthenticate(authentication));
	}

	private Authentication doAuthenticate(Authentication authentication) {
		if (authentication != null && apiKeyValidator.isApiKeyValid(authentication.getCredentials())) {
			log.info("API Key authentication successful");
			authentication.setAuthenticated(true);
		} else {
			log.warn("API Key authentication failed");
		}
		return authentication;
	}
}
