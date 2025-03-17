package org.iorio.core.repository.remote.graphql;

import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record RemoteCollectionQL(List<AbstractRepositoryFile<String>> files, List<AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>> directories) implements RepositoryCollection<String, AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>, AbstractRepositoryFile<String>> {
    public RemoteCollectionQL(final List<AbstractRepositoryFile<String>> files, final List<AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>> directories) {
        this.files = Objects.requireNonNull(files);
        this.directories = Objects.requireNonNull(directories);
    }
    @Override
    public List<AbstractRepositoryFile<String>> getFiles() {
        return Collections.unmodifiableList(this.files);
    }
    @Override
    public List<AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>> getDirectories() {
        return Collections.unmodifiableList(this.directories);
    }
}
