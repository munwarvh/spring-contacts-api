package nl.craftsmen.util;

import lombok.experimental.StandardException;

@StandardException
// TODO remove the constructors once the new Intellij Lombok plugin recognizes this
// See: https://github.com/mplushnikov/lombok-intellij-plugin/issues/1076
public class ResourceReaderException extends RuntimeException {

	private static final long serialVersionUID = -1164355789509986810L;

	public ResourceReaderException(String message) {
		super(message);
	}

	public ResourceReaderException(String message, Exception e) {
		super(message, e);
	}
}
