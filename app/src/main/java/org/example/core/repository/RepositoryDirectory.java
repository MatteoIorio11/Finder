package org.example.core.repository;

import java.util.List;

public interface RepositoryDirectory<P, Y extends RepositoryFile<P>> extends RepositoryElement<P>{
    List<Y> getFiles();
    List<RepositoryDirectory<P, Y>> getInnerDirectories();
    void addFile(Y file);
    void addDirectory(RepositoryDirectory<P, Y> directory);
}
