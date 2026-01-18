package nl.craftsmen.exceptionhandling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceNotFoundExceptionTest {

	private static final Long ID = 789L;

	@Test
	@DisplayName("When creating a ResourceNotFoundException with a provided id, I expect this exception to be "
			+ "constructed with a message containing the provided id")
	void expectAResourceNotFoundExceptionWithAMessageContainingTheId() {
		// GIVEN / WHEN
		final var exception = new ResourceNotFoundException(ID);
		final var expectedMessage = "Resource with id " + ID + " not found";

		// THEN
		assertThat(exception)
				.extracting(
						Throwable::getMessage,
						Throwable::getCause)
				.containsExactly(
						expectedMessage,
						null);
	}
}