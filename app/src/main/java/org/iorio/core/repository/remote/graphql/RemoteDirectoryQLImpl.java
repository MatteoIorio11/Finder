package org.iorio.core.repository.remote.graphql;

import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;

public class RemoteDirectoryQLImpl extends AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>> {
    public RemoteDirectoryQLImpl(final String name, final String path) {
        super(name, path);
    }
}
