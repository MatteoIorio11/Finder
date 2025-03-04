package org.example.core.repository;

import org.example.core.remote.RemoteDirectory;
import org.example.core.remote.RemoteFile;

import java.util.List;

public interface Repository<X, Y>{
    void addDirectory(X directory);
    void addFile(Y file);
    List<X> getDirectories();
    List<Y> getFiles();
}
