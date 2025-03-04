package org.example.core.repository;

import java.util.List;

public interface Repository<P, X extends RepositoryDirectory<P, Y>, Y extends RepositoryFile<P>>{
    void addDirectory(X directory);
    void addFile(Y file);
    List<X> getDirectories();
    List<Y> getFiles();
}
