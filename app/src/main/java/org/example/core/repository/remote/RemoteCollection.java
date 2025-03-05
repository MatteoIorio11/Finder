package org.example.core.repository.remote;

import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.RepositoryCollection;

import java.net.URL;
import java.util.List;

public record RemoteCollection(List<RemoteFile> files, List<AbstractRepositoryDirectory<URL, RemoteFile>> directories) implements RepositoryCollection<URL, AbstractRepositoryDirectory<URL, RemoteFile>, RemoteFile> {
    @Override
    public void setFiles(List<RemoteFile> files) {
        this.files.addAll(files);
    }

    @Override
    public void setDirectories(List<AbstractRepositoryDirectory<URL, RemoteFile>> directories) {
        this.directories.addAll(directories);
    }

    @Override
    public List<RemoteFile> getFiles() {
        return this.files;
    }

    @Override
    public List<AbstractRepositoryDirectory<URL, RemoteFile>> getDirectories() {
        return this.directories;
    }
}
