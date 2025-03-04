package org.example.core.repository;

import org.example.core.remote.RemoteDirectory;
import org.example.core.remote.RemoteFile;

import java.util.List;

public interface Repository {
    void addDirectory(RemoteDirectory directory);
    void addFile(RemoteFile file);
    List<RemoteDirectory> getRemoteDirectories();
    List<RemoteFile> getRemoteFiles();
}
