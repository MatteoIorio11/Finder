package org.example.core.repository;

import java.util.List;

public interface RepositoryCollection<P, X extends RepositoryDirectory<P, Y>, Y extends RepositoryFile<P>> {
    void setFiles(List<Y> files);
    void setDirectories(List<X> directories);
    List<Y> getFiles();
    List<X> getDirectories();
}
