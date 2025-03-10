package org.iorio.core.repository.remote;

import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;

import java.net.URL;
import java.util.Collections;
import java.util.List;

public record RemoteCollection(List<AbstractRepositoryFile<URL>> files, List<AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>> directories) implements RepositoryCollection<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> {
    @Override
    public List<AbstractRepositoryFile<URL>> getFiles() {
        return Collections.unmodifiableList(this.files);
    }
    @Override
    public List<AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>> getDirectories() {
        return Collections.unmodifiableList(this.directories);
    }
}
