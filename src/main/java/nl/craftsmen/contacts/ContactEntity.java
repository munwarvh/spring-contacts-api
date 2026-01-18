package nl.craftsmen.contacts;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "CONTACT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class ContactEntity extends BaseEntity {

	private static final long serialVersionUID = 1234562780639865862L;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String address1;

	private String address2;

	private String address3;

	@NotNull
	private String zipcode;

	@NotNull
	private String city;

	private String state;

	private String phone;

	private String email;

	@NotNull
	private String iban;

	private String socialSecurityNumber;

	private LocalDate dateOfDeath;

	@NotNull
	private LocalDate dateOfBirth;
}
