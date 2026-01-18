package nl.craftsmen.contacts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nl.craftsmen.contacts.ContactsTestdataSupplier.FIRST_NAME;
import static nl.craftsmen.contacts.ContactsTestdataSupplier.IBAN;
import static nl.craftsmen.contacts.ContactsTestdataSupplier.LAST_NAME;
import static nl.craftsmen.contacts.ContactsTestdataSupplier.SOCIAL_SECURITY_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContactSearchCriteriaToContactEntityMapperTest {

    @Test
    @DisplayName("Given some search criteria, I expect a ContactEntity to be created containing the values of the "
            + "fields in the search criteria")
    void mapSearchCriteriaToContactEntity() {
        // GIVEN
        final var searchCriteria = ContactSearchCriteria.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .socialSecurityNumber(SOCIAL_SECURITY_NUMBER)
                .iban(IBAN)
                .build();
        final var cut = new ContactSearchCriteriaToContactEntityMapper();

        // WHEN
        final var result = cut.mapToEntity(searchCriteria);

        // THEN
        assertThat(cut)
                .extracting(
                        e -> result.getFirstName(),
                        e -> result.getLastName(),
                        e -> result.getSocialSecurityNumber(),
                        e -> result.getIban())
                .containsExactly(
                        FIRST_NAME,
                        LAST_NAME,
                        SOCIAL_SECURITY_NUMBER,
                        IBAN);
    }
}