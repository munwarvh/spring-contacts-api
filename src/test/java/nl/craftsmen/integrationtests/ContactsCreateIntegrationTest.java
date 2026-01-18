package nl.craftsmen.integrationtests;

import nl.craftsmen.contacts.Contact;
import nl.craftsmen.contacts.ContactsTestdataSupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static nl.craftsmen.contacts.ContactsTestdataSupplier.createContact;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactsCreateIntegrationTest extends BaseIntegrationTest {

    private static final String HEADER_NAME_APIKEY = "apikey";
    private static final String APIKEY_VALUE = "dummyContactsApikey";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("When posting a contact, I expect a the contacted is created in the database and returned with an id")
    void expect_contact_in_database_with_specified_data() {
        // GIVEN / WHEN
        final var contact = createContact();
        final var result = webTestClient.post()
                .uri(ContactsTestdataSupplier.getUriForPost())
                .header(HEADER_NAME_APIKEY, APIKEY_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(contact), Contact.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Contact.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectNextMatches(this::expectContact)
                .verifyComplete();
    }

    private boolean expectContact(Contact contact) {
        assertThat(contact.getId()).isNotNull();
        return true;
    }
}