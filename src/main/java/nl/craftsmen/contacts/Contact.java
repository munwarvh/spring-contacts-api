package nl.craftsmen.contacts;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonPropertyOrder({
		"id",
		"firstName",
		"lastName",
		"address1",
		"address2",
		"address3",
		"zipcode",
		"city",
		"state",
		"phone",
		"email",
		"iban",
		"socialSecurityNumber",
		"death",
		"birth"
})
public class Contact {

	private Long id;

	@NotNull
	@Size(max = 30)
	private String firstName;

	@NotNull
	@Size(max = 30)
	private String lastName;

	@NotNull
	@Size(max = 30)
	private String address1;

	@Size(max = 30)
	private String address2;

	@Size(max = 30)
	private String address3;

	@NotNull
	@Size(max = 20)
	private String zipcode;

	@NotNull
	@Size(max = 30)
	private String city;

	@Size(max = 30)
	private String state;

	@Size(max = 25)
	private String phone;

	@Size(max = 50)
	private String email;

	@NotNull
	@Size(max = 34)
	private String iban;

	@Size(max = 25)
	private String socialSecurityNumber;

	private LocalDate dateOfDeath;

	@NotNull
	private LocalDate dateOfBirth;
}
