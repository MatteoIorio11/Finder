package org.example.core.repository.remote;

import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.RepositoryDirectory;
import org.example.core.repository.RepositoryFile;

import java.net.URL;
import java.util.List;

public record RemoteCollection(List<RemoteFile> files, List<RemoteDirectory> directories) implements RepositoryCollection<URL, RemoteDirectory, RemoteFile> {
    @Override
    public void setFiles(List<RemoteFile> files) {
        this.files.addAll(files);
    }

    @Override
    public void setDirectories(List<RemoteDirectory> directories) {
        this.directories.addAll(directories);
    }

    @Override
    public List<RemoteFile> getFiles() {
        return this.files;
    }

    @Override
    public List<RemoteDirectory> getDirectories() {
        return this.directories;
    }
}
