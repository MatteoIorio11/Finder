package org.example.core.repository.remote;


import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;

import java.net.URL;

public class RemoteDirectoryImpl extends AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> implements RemoteElement {
    public RemoteDirectoryImpl(final String name, final URL remoteUrl) {
        super(name, remoteUrl);
    }
}
