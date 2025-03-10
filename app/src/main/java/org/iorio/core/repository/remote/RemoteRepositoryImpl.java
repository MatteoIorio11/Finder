package org.iorio.core.repository.remote;

import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;

import java.net.URL;

public class RemoteRepositoryImpl extends AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> implements RemoteElement {
    public RemoteRepositoryImpl(final String name, final URL path) {
        super(name, path);
    }
}
