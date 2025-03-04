package org.example.core.repository;

import java.util.List;

public interface Repository<X extends RepositoryDirectory, Y extends RepositoryFile>{
    void addDirectory(X directory);
    void addFile(Y file);
    List<X> getDirectories();
    List<Y> getFiles();
}
