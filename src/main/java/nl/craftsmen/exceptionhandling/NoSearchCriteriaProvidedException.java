package nl.craftsmen.exceptionhandling;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@StandardException
// TODO remove the constructors once the new Intellij Lombok plugin recognizes this
// See: https://github.com/mplushnikov/lombok-intellij-plugin/issues/1076
public class NoSearchCriteriaProvidedException extends RuntimeException {

    private static final long serialVersionUID = -4642575837372383628L;

    private static final String MESSAGE = "No search criteria provided!";

    public NoSearchCriteriaProvidedException() {
        super(MESSAGE);
    }
}
