package nl.craftsmen.contacts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ContactCreateServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ContactRepository repository;

    @Mock
    private Contact contact;

    @Mock
    private ContactEntity contactEntity;

    @InjectMocks
    private ContactCreateService cut;

    @Test
    @DisplayName("Given a contact, I expect it to be created and returned")
    void expectContactToBeCreatedAndReturned() {
        // GIVEN
        given(modelMapper.map(contact, ContactEntity.class)).willReturn(contactEntity);
        given(repository.save(contactEntity)).willReturn(contactEntity);
        given(modelMapper.map(contactEntity, Contact.class)).willReturn(contact);

        // WHEN
        final var result = cut.create(contact);

        // THEN
        expectContact(result, contact);
    }

    private void expectContact(Mono<Contact> result, Contact contact) {
        StepVerifier.create(result)
                .expectNext(contact)
                .verifyComplete();
    }
}