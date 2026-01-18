package nl.craftsmen.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public final class ResourceReader {

	private ResourceReader() {
		throw new UnsupportedOperationException("Cannot initialize utility classes");
	}

	public static String readClasspathFileContentAsString(String resourcePath) {
		final var classPathResource = new ClassPathResource(resourcePath);
		try (Reader reader = new InputStreamReader(classPathResource.getInputStream(), StandardCharsets.UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new ResourceReaderException("Error occurred reading file " + classPathResource.getFilename(), e);
		}
	}
}