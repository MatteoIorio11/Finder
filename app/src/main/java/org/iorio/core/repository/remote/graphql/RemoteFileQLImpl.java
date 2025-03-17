package org.iorio.core.repository.remote.graphql;

import org.iorio.core.repository.AbstractRepositoryFile;

public class RemoteFileQLImpl extends AbstractRepositoryFile<String> {
    /**
     * Constructor for AbstractRepositoryFile
     *
     * @param name the name of the file
     * @param path the path of the file
     */
    public RemoteFileQLImpl(String name, String path) {
        super(name, path);
    }
}
