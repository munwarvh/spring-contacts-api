package nl.craftsmen.contacts;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.craftsmen.contacts.validation.ValidatorUtil;

import nl.jqno.equalsverifier.EqualsVerifier;

class ContactEntityTest {

	private final ContactEntity contactEntity1 = ContactEntity.builder().id(7L).build();
	private final ContactEntity contactEntity2 = ContactEntity.builder().id(8L).build();

	private static Validator validator;

	@BeforeAll
	static void setup() {
		validator = ValidatorUtil.getValidator();
	}

	@Test
	@DisplayName("Given a ContactEntity without required fields, I expect 7 errors when validation is called")
	void constructContactWithoutRequiredFieldsResultsInConstraintViolations() {
		// GIVEN
		final var contact = ContactEntity.builder().build();

		// WHEN
		final var violations = validator.validate(contact);

		// THEN
		assertThat(violations)
				.hasSize(7)
				.map(p -> p.getPropertyPath().toString())
				.contains("firstName", "lastName", "address1", "zipcode", "city", "iban", "dateOfBirth");
	}

	@Test
	@DisplayName("Given a ContactEntity, I expect that it meets the equals and hascode contract")
	void equalsHashCodeContracts() {
		final var contactEntity1 = ContactEntity.builder().id(95L).build();
		final var contactEntity2 = ContactEntity.builder().id(96L).build();
		EqualsVerifier.forClass(ContactEntity.class)
				.usingGetClass()
				.withPrefabValues(ContactEntity.class, contactEntity1, contactEntity2)
				.withOnlyTheseFields("id")
				.verify();
	}
}