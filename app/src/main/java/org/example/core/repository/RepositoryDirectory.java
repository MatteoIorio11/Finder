package org.example.core.repository;

import java.util.List;

public interface RepositoryDirectory<Y extends RepositoryFile> extends RepositoryElement{
    List<Y> getFiles();
    List<RepositoryDirectory<Y>> getInnerDirectories();
    void addFile(Y file);
    void addDirectory(RepositoryDirectory<Y> directory);
}
