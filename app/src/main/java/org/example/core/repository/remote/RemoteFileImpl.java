package org.example.core.repository.remote;


import org.example.core.repository.AbstractRepositoryFile;

import java.net.URL;
import java.util.Objects;

public class RemoteFileImpl extends AbstractRepositoryFile<URL> {
    public RemoteFileImpl(final String name, final URL url) {
        super(name, url);
    }

}
