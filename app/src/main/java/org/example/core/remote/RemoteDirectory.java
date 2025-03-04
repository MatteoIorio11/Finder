package org.example.core.remote;

import org.example.core.repository.RepositoryDirectory;
import org.example.core.repository.RepositoryFile;

import java.util.List;


public interface RemoteDirectory<Y extends RepositoryFile> extends RemoteElement, RepositoryDirectory<Y> {

}
