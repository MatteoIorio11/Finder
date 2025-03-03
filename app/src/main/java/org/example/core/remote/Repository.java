package org.example.core.remote;

import java.util.List;

public interface Repository {
    void addDirectory(RemoteDirectory directory);
    void addFile(RemoteFile file);
    List<RemoteDirectory> getRemoteDirectories();
    List<RemoteFile> getRemoteFiles();
}
