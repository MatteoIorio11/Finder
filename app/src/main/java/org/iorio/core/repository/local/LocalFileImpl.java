package org.iorio.core.repository.local;

import org.iorio.core.repository.AbstractRepositoryFile;

import java.nio.file.Path;

public class LocalFileImpl extends AbstractRepositoryFile<Path> implements LocalElement {
    public LocalFileImpl(final String name, final Path path) {
        super(name, path);
    }
}
