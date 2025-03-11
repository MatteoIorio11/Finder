package org.iorio.core.repository.local;

import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public record LocalCollection(List<AbstractRepositoryFile<Path>> files, List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories) implements RepositoryCollection<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> {
    public LocalCollection(final List<AbstractRepositoryFile<Path>> files, final List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories) {
        this.files = Objects.requireNonNull(files);
        this.directories = Objects.requireNonNull(directories);
    }
    @Override
    public List<AbstractRepositoryFile<Path>> getFiles() {
        return this.files;
    }

    @Override
    public List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> getDirectories() {
        return this.directories;
    }
}
