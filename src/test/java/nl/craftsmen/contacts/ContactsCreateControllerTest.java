package nl.craftsmen.contacts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ContactsCreateControllerTest {

	@Mock
	private ContactCreateService contactCreateServiceMock;

	@Mock
	private Contact contactMock;

	@InjectMocks
	private ContactsCreateController controller;

	@Test
	@DisplayName("When post is called with a contact in the body, I expect a response 200 and the contact with an id "
			+ "to be returned and that serviceMock create method has been called with the provided contact")
	void postContactExpectResposne200AndContactToBeReturnedAndServiceMockCreateHasBeenCalled() {
		// GIVEN
		final var expectedResult = Mono.just(contactMock);
		when(contactCreateServiceMock.create(contactMock)).thenReturn(expectedResult);

		// WHEN
		final var result = controller.post(contactMock);

		// THEN
		StepVerifier.create(result)
				.assertNext(r -> assertThat(r).isEqualTo(contactMock))
				.verifyComplete();
		verify(contactCreateServiceMock).create(contactMock);
	}
}