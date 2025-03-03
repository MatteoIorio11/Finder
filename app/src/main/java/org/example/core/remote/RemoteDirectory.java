package org.example.core.remote;

import java.util.List;

public interface RemoteDirectory {
    String getName();
    List<RemoteFile> getFiles();
    void addFile(RemoteFile file);
    void addDirectory(RemoteDirectory directory);
}
