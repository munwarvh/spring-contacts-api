package nl.craftsmen.contacts;

import nl.craftsmen.exceptionhandling.NoSearchCriteriaProvidedException;
import nl.craftsmen.exceptionhandling.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static nl.craftsmen.contacts.ContactsTestdataSupplier.CONTACT_ID;
import static nl.craftsmen.contacts.ContactsTestdataSupplier.FIRST_NAME;
import static nl.craftsmen.contacts.ContactsTestdataSupplier.SOCIAL_SECURITY_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ContactReadServiceTest {

    private static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource with id " + CONTACT_ID + " not found";

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ContactSearchCriteriaToContactEntityMapper searchCriteriaMapper;

    @Mock
    private ContactRepository repository;

    @Mock
    private Contact contact1;

    @Mock
    private Contact contact2;

    @Mock
    private ContactEntity contactEntity1;

    @Mock
    private ContactEntity contactEntity2;

    @InjectMocks
    private ContactReadService cut;

    @Test
    @DisplayName("When searching a contact by id, I expect a contact to be returned given that id")
    void expectToFindContactWhenFindingContactById() {
        // GIVEN
        given(repository.findById(CONTACT_ID)).willReturn(Optional.of(contactEntity1));
        given(modelMapper.map(contactEntity1, Contact.class)).willReturn(contact1);

        // WHEN
        final var result = cut.findById(CONTACT_ID);

        // THEN
        expectContact(result, contact1);
    }

    @Test
    @DisplayName("When searching a contact with a non-existing id, I expect to receive a ResourceNotFoundException")
    void expectResourceNotFoundExceptionWhenSearchingContactWithNonExistingId() {
        // GIVEN
        given(repository.findById(CONTACT_ID)).willReturn(Optional.empty());

        // WHEN
        final var result = cut.findById(CONTACT_ID);

        // THEN
        expectResourceNotFound(result);
    }

    @Test
    @DisplayName("When searching contacts on first name, I expect two results")
    void expectTwoResultsWhenSearchingContactsOnFirstName() {
        // GIVEN
        final var searchCriteria = ContactSearchCriteria.builder().firstName(FIRST_NAME).build();
        final var contactEntity = ContactEntity.builder().firstName(FIRST_NAME).build();
        final var contactEntityExample = Example.of(contactEntity);
        given(searchCriteriaMapper.mapToEntity(searchCriteria)).willReturn(contactEntity);
        given(repository.findAll(contactEntityExample)).willReturn(List.of(contactEntity1, contactEntity2));
        given(modelMapper.map(contactEntity1, Contact.class)).willReturn(contact1);
        given(modelMapper.map(contactEntity2, Contact.class)).willReturn(contact2);

        // WHEN
        final var result = cut.findByCriteria(searchCriteria);

        // THEN
        expectTwoContactsFound(result, contact1, contact2);
    }

    @Test
    @DisplayName("When searching on socialSecurityNumber, I expect that nothing is found and no result is "
            + "being returned")
    void expectNoResulsWhenSearchingOnSocialSecurityNumber() {
        // GIVEN
        final var searchCriteria = ContactSearchCriteria.builder().firstName(SOCIAL_SECURITY_NUMBER).build();
        final var contactEntity = ContactEntity.builder().firstName(SOCIAL_SECURITY_NUMBER).build();
        final var contactEntityExample = Example.of(contactEntity);
        given(searchCriteriaMapper.mapToEntity(searchCriteria)).willReturn(contactEntity);
        given(repository.findAll(contactEntityExample)).willReturn(Collections.emptyList());

        // WHEN
        final var result = cut.findByCriteria(searchCriteria);

        // THEN
        expectNoResultFound(result);
    }

    @Test
    @DisplayName("When searching without any search criteria, I expect a NoSearchCriteriaProvidedException")
    void expectNoSearchCriteriaProvidedExceptionWhenSearchingWithoutSearchCriteria() {
        // GIVEN
        final var searchCriteria = ContactSearchCriteria.builder().build();

        // WHEN
        final var thrown = catchThrowable(() -> cut.findByCriteria(searchCriteria));

        // THEN
        assertThat(thrown)
                .isInstanceOf(NoSearchCriteriaProvidedException.class)
                .hasMessage("No search criteria provided!");
    }

    private void expectContact(Mono<Contact> result, Contact contact) {
        StepVerifier.create(result)
                .expectNext(contact)
                .verifyComplete();
    }

    private void expectResourceNotFound(Mono<Contact> result) {
        StepVerifier.create(result)
                .expectErrorMatches(this::assertResourceNotFoundException)
                .verify();
    }

    private boolean assertResourceNotFoundException(Throwable throwable) {
        assertThat(throwable)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(RESOURCE_NOT_FOUND_MESSAGE);
        return true;
    }

    void expectTwoContactsFound(Flux<Contact> result, Contact... contacts) {
        StepVerifier
                .create(result)
                .expectNext(contacts[0])
                .expectNext(contacts[1])
                .verifyComplete();
    }

    private void expectNoResultFound(Flux<Contact> result) {
        // verifyComplete is sufficient to check empty result; if there would be a result, the check would fail
        // (because an onNext would be expected)
        StepVerifier
                .create(result)
                .verifyComplete();
    }
}