package nl.craftsmen.contacts;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
@Validated
@Slf4j
public class ContactCreateService {

	private final ModelMapper modelMapper;
	private final ContactRepository repository;

	public Mono<Contact> create(Contact contact) {
		return Mono.just(contact)
				.publishOn(Schedulers.boundedElastic())
				.map(this::storeContact)
				.map(e -> modelMapper.map(e, Contact.class));
	}

	@Transactional
	private ContactEntity storeContact(Contact contact) {
		log.info("Create contact with firstName {} and lastName {}", contact.getFirstName(), contact.getLastName());
		return repository.save(modelMapper.map(contact, ContactEntity.class));
	}
}
