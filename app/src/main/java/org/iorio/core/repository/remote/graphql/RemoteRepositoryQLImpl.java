package org.iorio.core.repository.remote.graphql;

import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.html.RemoteElement;


public class RemoteRepositoryQLImpl extends AbstractRepository<String, AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>, AbstractRepositoryFile<String>> implements RemoteElementQL {
    public RemoteRepositoryQLImpl(final String name, final String path) {
        super(name, path);
    }
}
