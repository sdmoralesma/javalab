package com.smorales.javalab.business.processor.boundary.rest.model;

import com.smorales.javalab.business.processor.boundary.NotRunnableCodeException;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LanguageSourcesReader {

    public static final String HELLO_WORLD_REGEX = "HelloWorld\\.(java|scala|groovy)";
    public static final String HELLO_WORLD_TEST_REGEX = "HelloWorldTest\\.(java|scala|groovy)";
    public static final String INIT_DEPS_REGEX = "init-deps";

    @Inject
    Logger tracer;

    public Map<String, String> readFilesRecursively(final Path path) {
        try {
            return Files.walk(path)
                    .filter(p -> !p.toFile().isDirectory())
                    .filter(p -> p.getFileName().toString().matches(HELLO_WORLD_REGEX)
                            || p.getFileName().toString().matches(HELLO_WORLD_TEST_REGEX)
                            || p.getFileName().toString().matches(INIT_DEPS_REGEX))
                    .collect(Collectors.toMap(p -> p.getFileName().toString(), this::linesByFile));
        } catch (final IOException e) {
            tracer.severe(e.getMessage());
            throw new NotRunnableCodeException("Cannot walk path: " + path);
        }
    }

    private String linesByFile(final Path path) {
        try {
            return new String(Files.readAllBytes(path), Charset.forName("UTF-8"));
        } catch (final IOException e) {
            tracer.severe(e.getMessage());
            throw new NotRunnableCodeException("Cannot read file: " + path);
        }
    }
}
