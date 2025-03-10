package org.iorio.core.repository.local;

import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;

import java.nio.file.Path;

public class LocalDirectoryImpl extends AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> implements LocalElement{
    public LocalDirectoryImpl(final String name, final Path path) {
        super(name, path);
    }
}
