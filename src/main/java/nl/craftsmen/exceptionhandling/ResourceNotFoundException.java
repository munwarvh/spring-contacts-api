package nl.craftsmen.exceptionhandling;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.experimental.StandardException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@StandardException
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8907085101881944796L;

	public ResourceNotFoundException(@NotNull Long id) {
		super("Resource with id " + id + " not found");
	}
}
