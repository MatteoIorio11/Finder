package org.example.core.repository.remote;

import org.example.core.repository.RepositoryDirectory;
import org.example.core.repository.RepositoryFile;

import java.net.URL;


public interface RemoteDirectory extends RemoteElement, RepositoryDirectory<URL, RemoteFile>{

}
