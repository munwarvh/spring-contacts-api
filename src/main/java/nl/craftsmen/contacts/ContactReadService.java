package nl.craftsmen.contacts;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.validation.Valid;

import nl.craftsmen.exceptionhandling.NoSearchCriteriaProvidedException;
import nl.craftsmen.exceptionhandling.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
@Validated
@Slf4j
public class ContactReadService {

    private final ModelMapper modelMapper;
    private final ContactSearchCriteriaToContactEntityMapper searchCriteriaMapper;
    private final ContactRepository repository;

    public Mono<Contact> findById(Long id) {
        return scheduleBlockingCallable(() -> repository.findById(id)
                .map(e -> modelMapper.map(e, Contact.class))).filter(Optional::isPresent).map(Optional::get)
                .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException(id)));
    }

    public Flux<Contact> findByCriteria(@Valid ContactSearchCriteria searchCriteria) {
        if (searchCriteria.isNoSearchCriteriaProvided()) {
            throw new NoSearchCriteriaProvidedException();
        }
        return findByCriteria(searchCriteriaMapper.mapToEntity(searchCriteria));
    }

    private Flux<Contact> findByCriteria(ContactEntity contactEntity) {
        return scheduleBlockingCallable(() -> blockingFindAllByCriteria(contactEntity)).subscribeOn(Schedulers.immediate())
                .flatMapMany(Flux::fromIterable);
    }

    private List<Contact> blockingFindAllByCriteria(ContactEntity contactEntity) {
        return repository.findAll(Example.of(contactEntity)).stream().map(e -> modelMapper.map(e, Contact.class))
                .collect(Collectors.toList());
    }

    private static <T> Mono<T> scheduleBlockingCallable(Callable<T> blokkingCallable) {
        return Mono.fromCallable(blokkingCallable).subscribeOn(Schedulers.boundedElastic());
    }
}
