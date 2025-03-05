package org.example.core.repository.local;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;

import java.nio.file.Path;

public class LocalRepositoryImpl extends AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> {
    public LocalRepositoryImpl(final String name, final Path path) {
        super(name, path);
    }
}
