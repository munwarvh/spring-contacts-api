package nl.craftsmen.contacts;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ContactsCreateController.RESOURCE)
@AllArgsConstructor
@Slf4j
public class ContactsCreateController {

	static final String RESOURCE = "/contacts";

	private final ContactCreateService contactCreateService;

	@PostMapping()
	public Mono<Contact> post(@Valid @RequestBody Contact contact) {
		log.info("========================================");
		log.info(">>>>> POST /contacts endpoint HIT!");
		log.info(">>>>> Creating contact: {} {}", contact.getFirstName(), contact.getLastName());
		log.info("========================================");
		return contactCreateService.create(contact)
				.doOnSuccess(created -> log.info(">>>>> Contact created successfully with ID: {}", created.getId()))
				.doOnError(error -> log.error(">>>>> Failed to create contact: {}", error.getMessage()));
	}
}
