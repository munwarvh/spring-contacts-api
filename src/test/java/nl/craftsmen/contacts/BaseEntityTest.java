package nl.craftsmen.contacts;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BaseEntityTest {

	private static final Long ID = 999L;
	private static final Long VERSION = 1L;

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

	@Test
	@DisplayName("Given a ContactEntity with an id, when calling the toString method, I expect the result contains "
			+ "an id")
	void toStringTest() {
		// GIVEN
		final var baseEntity = ContactEntity.builder()
				.id(ID)
				.version(VERSION)
				.build();

		// WHEN
		final var result = baseEntity.toString();

		// THEN
		assertThat(result).isEqualTo("{\"id\":999}");
	}
}
