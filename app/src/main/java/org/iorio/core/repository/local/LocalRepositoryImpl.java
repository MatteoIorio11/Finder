package org.iorio.core.repository.local;

import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;

import java.nio.file.Path;

public class LocalRepositoryImpl extends AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> {
    public LocalRepositoryImpl(final String name, final Path path) {
        super(name, path);
    }
}
