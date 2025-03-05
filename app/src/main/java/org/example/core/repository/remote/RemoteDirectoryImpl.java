package org.example.core.repository.remote;


import org.example.core.repository.AbstractRepositoryDirectory;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RemoteDirectoryImpl extends AbstractRepositoryDirectory<URL, RemoteFile>{
    public RemoteDirectoryImpl(final String name, final URL remoteUrl) {
        super(name, remoteUrl);
    }
}
