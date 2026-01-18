package nl.craftsmen.contacts;


import static nl.craftsmen.contacts.ContactsTestdataSupplier.CONTACT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ContactsReadControllerTest {

    private static final String SOCIAL_SECURITY_NUMBER = "408-03-7262";

    @Mock
    private ContactReadService contactReadServiceMock;

    @Mock
    private Contact contactMock;

    @Captor
    private ArgumentCaptor<ContactSearchCriteria> contactSearchCriteriaArgumentCaptor;

    @InjectMocks
    private ContactsReadController cut;

    @Test
    @DisplayName("When calling resource method getById, I expect a response 200 and a contact to be returned and that "
            + "serviceMock findById method has been called with a contact id")
    void getByIdExpectResponse200AndAContactAndServiceMockFindByIdHasBeenCalled() {
        // GIVEN
        final var expectedResult = Mono.just(contactMock);
        given(contactReadServiceMock.findById(CONTACT_ID)).willReturn(expectedResult);

        // WHEN
        final var result = cut.get(CONTACT_ID);

        // THEN
        expectAContactAndContactServiceMockFindByIdWithContactIdHasBeenCalled(result);
    }

    private void expectAContactAndContactServiceMockFindByIdWithContactIdHasBeenCalled(Mono<Contact> result) {
        StepVerifier.create(result)
                .assertNext(r -> assertThat(r).isEqualTo(contactMock))
                .verifyComplete();
        verify(contactReadServiceMock).findById(CONTACT_ID);
    }

    @Test
    @DisplayName("When calling resource method getByCriteria with a social security number, I expect a response 200 "
            + "and a contact to be returned and that serviceMock findByCriteria has been called with the social "
            + "security number provided")
    void getByCriteriaExpectResponse200AndAContactToBeReturnedAndServiceMockFindByCriteriaHasBeenCalled() {
        // GIVEN
        final var expectedResult = Flux.fromIterable(List.of(contactMock));
        given(contactReadServiceMock.findByCriteria(any())).willReturn(expectedResult);

        // WHEN
        final var result = cut.getByCriteria(Optional.empty(), Optional.empty(), Optional.of(SOCIAL_SECURITY_NUMBER),
                Optional.empty());

        // THEN
        expectContactAndContactServiceMockFindByCriteriaWithSearchCriteriaHasBeenCalled(result);
    }

    private void expectContactAndContactServiceMockFindByCriteriaWithSearchCriteriaHasBeenCalled(Flux<Contact> result) {
        verify(contactReadServiceMock).findByCriteria(contactSearchCriteriaArgumentCaptor.capture());
        final var searchCriteria = contactSearchCriteriaArgumentCaptor.getValue();

        assertThat(searchCriteria.getSocialSecurityNumber()).isEqualTo(SOCIAL_SECURITY_NUMBER);

        StepVerifier.create(result)
                .assertNext(r -> assertThat(r).isEqualTo(contactMock))
                .verifyComplete();
    }
}