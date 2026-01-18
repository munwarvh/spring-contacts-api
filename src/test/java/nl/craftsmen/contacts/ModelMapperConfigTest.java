package nl.craftsmen.contacts;

import static org.assertj.core.api.Assertions.assertThat;

import nl.craftsmen.config.ModelMapperConfig;
import org.junit.jupiter.api.Test;
import org.modelmapper.config.Configuration;

class ModelMapperConfigTest {

	@Test
	void createModelMapper() {
		// GIVEN / WHEN
		final var modelMapper = new ModelMapperConfig().createModelMapper();

		// THEN
		assertThat(modelMapper.getConfiguration().isFieldMatchingEnabled()).isTrue();
		assertThat(modelMapper.getConfiguration().getFieldAccessLevel()).isEqualTo(Configuration.AccessLevel.PRIVATE);
	}
}