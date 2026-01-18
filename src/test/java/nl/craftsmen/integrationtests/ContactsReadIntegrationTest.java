package nl.craftsmen.integrationtests;

import static org.assertj.core.api.Assertions.assertThat;

import nl.craftsmen.contacts.ContactRepository;
import nl.craftsmen.contacts.ContactsTestdataSupplier;
import nl.craftsmen.contacts.ContactEntity;
import nl.craftsmen.contacts.Contact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactsReadIntegrationTest extends BaseIntegrationTest {

    private static final String HEADER_NAME_APIKEY = "apikey";
    private static final String APIKEY_VALUE = "dummyContactsApikey";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ContactRepository repository;

    @Test
    @DisplayName("When searching for a contact on social security number, I expect to find one contact with that "
            + "social security number provided")
    void expect_contact_with_specified_socialsecuritynumber() {
        // GIVEN
        ContactEntity contactEntity1 = ContactsTestdataSupplier.createContactEntity().toBuilder()
                .socialSecurityNumber(ContactsTestdataSupplier.SOCIAL_SECURITY_NUMBER)
                .id(1L)
                .build();
        ContactEntity contactEntity2 = ContactsTestdataSupplier.createContactEntity().toBuilder()
                .iban(ContactsTestdataSupplier.IBAN_2)
                .socialSecurityNumber("908-03-7269")
                .id(2L)
                .build();
        repository.save(contactEntity1);
        repository.save(contactEntity2);

        // WHEN
        final var result = Objects.requireNonNull(webTestClient.get()
                        .uri("/contacts?socialsecuritynumber=" + ContactsTestdataSupplier.SOCIAL_SECURITY_NUMBER)
                        .header(HEADER_NAME_APIKEY, APIKEY_VALUE)
                        .exchange()
                        .expectStatus()
                        .is2xxSuccessful()
                        .expectBodyList(Contact.class)
                        .hasSize(1)
                        .returnResult()
                        .getResponseBody())
                .get(0);

        // THEN
        assertThat(result.getSocialSecurityNumber()).isEqualTo(ContactsTestdataSupplier.SOCIAL_SECURITY_NUMBER);
    }
}