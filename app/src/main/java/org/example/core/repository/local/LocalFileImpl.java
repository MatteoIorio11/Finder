package org.example.core.repository.local;

import org.example.core.repository.AbstractRepositoryFile;

import java.nio.file.Path;
import java.util.Objects;

public class LocalFileImpl extends AbstractRepositoryFile<Path> implements LocalElement {
    public LocalFileImpl(final String name, final Path path) {
        super(name, path);
    }
}
