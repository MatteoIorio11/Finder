package org.iorio.core.repository.remote.html;


import org.iorio.core.repository.AbstractRepositoryFile;

import java.net.URL;

public class RemoteFileImpl extends AbstractRepositoryFile<URL> {
    public RemoteFileImpl(final String name, final URL url) {
        super(name, url);
    }

}
