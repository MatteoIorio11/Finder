package org.example.core.repository.local;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LocalRepositoryImpl implements LocalRepository {
    private final List<LocalFile> files;
    private final List<LocalDirectory> directories;
    public LocalRepositoryImpl() {
        this.files = new LinkedList<>();
        this.directories = new LinkedList<>();
    }
    @Override
    public void addDirectory(LocalDirectory directory) {
        this.directories.add(directory);
    }

    @Override
    public void addFile(LocalFile file) {
        this.files.add(file);
    }

    @Override
    public List<LocalDirectory> getDirectories() {
        return Collections.unmodifiableList(this.directories);
    }

    @Override
    public List<LocalFile> getFiles() {
        return Collections.unmodifiableList(this.files);
    }
}
