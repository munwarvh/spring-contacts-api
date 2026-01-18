package nl.craftsmen.contacts;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ContactSearchCriteriaToContactEntityMapper {

	public ContactEntity mapToEntity(ContactSearchCriteria searchCriteria) {
		return ContactEntity.builder()
				.firstName(searchCriteria.getFirstName())
				.lastName(searchCriteria.getLastName())
				.socialSecurityNumber(searchCriteria.getSocialSecurityNumber())
				.iban(searchCriteria.getIban())
				.build();
	}
}
