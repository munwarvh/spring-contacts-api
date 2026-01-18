package nl.craftsmen.integrationtests;

import nl.craftsmen.contacts.Contact;
import nl.craftsmen.contacts.ContactsTestdataSupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static nl.craftsmen.contacts.ContactsTestdataSupplier.createContact;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationCreateIntegrationTest extends BaseIntegrationTest {

    private static final String HEADER_NAME_APIKEY = "apikey";
    private static final String VALID_APIKEY_VALUE = "dummyContactsApikey";
    private static final String INVALID_API_KEY = "letsTryEvilHackerApiKey";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("When a post request is done with a valid apikey in the header, I expect a response 200 (OK)")
    void expect_200_response_when_apikey_is_valid() {
        final var contact = createContact();
        webTestClient.post()
                .uri(ContactsTestdataSupplier.getUriForPost())
                .header(HEADER_NAME_APIKEY, VALID_APIKEY_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(contact), Contact.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("When a post request is done with an invalid apikey in the header, I expect a response response 403 "
            + "(unauthorized)")
    void expect_403_response_when_apikey_is_invalid() {
        webTestClient.post()
                .uri(ContactsTestdataSupplier.getUriForPost())
                .header(HEADER_NAME_APIKEY, INVALID_API_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("When a post request is done with no apikey in the header, I expect a response 401 (unauthorized)")
    void expect_403_response_when_authentication_header_is_empty() {
        webTestClient.post()
                .uri(ContactsTestdataSupplier.getUriForPost())
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
