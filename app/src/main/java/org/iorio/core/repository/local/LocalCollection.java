package org.iorio.core.repository.local;

import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;

import java.nio.file.Path;
import java.util.List;

public record LocalCollection(List<AbstractRepositoryFile<Path>> files, List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories) implements RepositoryCollection<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> {
    @Override
    public List<AbstractRepositoryFile<Path>> getFiles() {
        return this.files;
    }

    @Override
    public List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> getDirectories() {
        return this.directories;
    }
}
