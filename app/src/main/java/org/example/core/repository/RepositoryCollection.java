package org.example.core.repository;

import java.util.List;

public interface RepositoryCollection<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>> {
    void setFiles(List<Y> files);
    void setDirectories(List<X> directories);
    List<Y> getFiles();
    List<X> getDirectories();
}
