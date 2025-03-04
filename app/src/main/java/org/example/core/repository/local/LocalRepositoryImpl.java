package org.example.core.repository.local;

import org.example.core.repository.Repository;

import java.util.List;

public class LocalRepository implements Repository<LocalDirectory<LocalFile>, LocalFile>{
    @Override
    public void addDirectory(LocalDirectory<LocalFile> directory) {
        
    }

    @Override
    public void addFile(LocalFile file) {

    }

    @Override
    public List<LocalDirectory<LocalFile>> getDirectories() {
        return null;
    }

    @Override
    public List<LocalFile> getFiles() {
        return null;
    }
}
