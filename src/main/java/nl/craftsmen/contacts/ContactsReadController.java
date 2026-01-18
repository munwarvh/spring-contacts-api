package nl.craftsmen.contacts;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ContactsReadController.RESOURCE)
@AllArgsConstructor
@Slf4j
public class ContactsReadController {

    static final String RESOURCE = "/contacts";

    private final ContactReadService contactReadService;

    @GetMapping("/{id}")
    public Mono<Contact> get(@PathVariable("id") Long id) {
        log.info("========================================");
        log.info(">>>>> GET /contacts/{} endpoint HIT!", id);
        log.info("========================================");
        return contactReadService.findById(id)
                .doOnSuccess(contact -> {
                    if (contact != null) {
                        log.info(">>>>> Contact found: {} {}", contact.getFirstName(), contact.getLastName());
                    } else {
                        log.warn(">>>>> Contact with ID {} not found", id);
                    }
                })
                .doOnError(error -> log.error(">>>>> Failed to get contact by ID {}: {}", id, error.getMessage()));
    }

    @GetMapping()
    public Flux<Contact> getByCriteria(
            @RequestParam("firstname") Optional<String> firstName,
            @RequestParam("lastname") Optional<String> lastName,
            @RequestParam("socialsecuritynumber") Optional<String> socialSecurityNumber,
            @RequestParam("iban") Optional<String> iban) {
        log.info("========================================");
        log.info(">>>>> GET /contacts (search) endpoint HIT!");
        log.info(">>>>> Search criteria - firstname: {}, lastname: {}, ssn: {}, iban: {}",
                firstName.orElse("N/A"), lastName.orElse("N/A"),
                socialSecurityNumber.orElse("N/A"), iban.orElse("N/A"));
        log.info("========================================");

        // Map all incoming request parameters to search criteria model.
        final var searchCriteria = ContactSearchCriteria.builder()
                .firstName(firstName.orElse(null))
                .lastName(lastName.orElse(null))
                .socialSecurityNumber(socialSecurityNumber.orElse(null))
                .iban(iban.orElse(null)).build();
        return contactReadService.findByCriteria(searchCriteria)
                .doOnComplete(() -> log.info(">>>>> Search completed"))
                .doOnError(error -> log.error(">>>>> Search failed: {}", error.getMessage()));
    }
}
