package org.example.core.repository.local;

import org.example.core.repository.RepositoryCollection;

import java.nio.file.Path;
import java.util.List;

public record LocalCollection(List<LocalFile> files, List<LocalDirectory> directories) implements RepositoryCollection<Path, LocalDirectory, LocalFile> {
    @Override
    public void setFiles(List<LocalFile> files) {
        this.files.addAll(files);
    }

    @Override
    public void setDirectories(List<LocalDirectory> directories) {
        this.directories.addAll(directories);
    }

    @Override
    public List<LocalFile> getFiles() {
        return this.files;
    }

    @Override
    public List<LocalDirectory> getDirectories() {
        return this.directories;
    }
}
