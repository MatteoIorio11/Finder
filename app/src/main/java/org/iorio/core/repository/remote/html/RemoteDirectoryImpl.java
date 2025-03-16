package org.iorio.core.repository.remote.html;


import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;

import java.net.URL;

public class RemoteDirectoryImpl extends AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> implements RemoteElement {
    public RemoteDirectoryImpl(final String name, final URL remoteUrl) {
        super(name, remoteUrl);
    }
}
