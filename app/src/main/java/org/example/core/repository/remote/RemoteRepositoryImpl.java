package org.example.core.repository.remote;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.RepositoryElement;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class RemoteRepositoryImpl extends AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> implements RemoteElement {
    public RemoteRepositoryImpl(final String name, final URL path) {
        super(name, path);
    }
}
