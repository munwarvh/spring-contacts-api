package nl.craftsmen.contacts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactSearchCriteria {
	private String firstName;
	private String lastName;
	private String socialSecurityNumber;
	private String iban;

	public boolean isNoSearchCriteriaProvided() {
		return Stream.of(firstName, lastName, socialSecurityNumber, iban).allMatch(Objects::isNull);
	}
}
