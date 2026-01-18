package nl.craftsmen.exceptionhandling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NoSearchCriteriaProvidedExceptionTest {

    @Test
    @DisplayName("When creating a NoSearchCriteriaProvidedException, I expect this exception to be constructed with a "
            + "message")
    void expectAResourceNotFoundExceptionWithAMessageContainingTheId() {
        // GIVEN / WHEN
        final var exception = new NoSearchCriteriaProvidedException();
        final var expectedMessage = "No search criteria provided!";

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