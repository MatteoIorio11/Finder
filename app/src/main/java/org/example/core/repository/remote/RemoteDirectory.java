package org.example.core.repository.remote;

import org.example.core.repository.RepositoryDirectory;
import org.example.core.repository.RepositoryFile;


public interface RemoteDirectory<Y extends RepositoryFile> extends RemoteElement, RepositoryDirectory<Y> {

}
