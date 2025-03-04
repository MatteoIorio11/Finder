package org.example.core.repository;

import org.example.core.remote.RemoteDirectory;
import org.example.core.remote.RemoteFile;
import org.example.core.repository.Repository;

import java.util.LinkedList;
import java.util.List;

public class RepositoryImpl implements Repository<RemoteDirectory, RemoteFile> {

    private final List<RemoteDirectory> remoteDirectories;
    private final List<RemoteFile> remoteFiles;

    public RepositoryImpl() {
        this.remoteDirectories = new LinkedList<>();
        this.remoteFiles = new LinkedList<>();
    }
    @Override
    public void addDirectory(RemoteDirectory directory) {
        this.remoteDirectories.add(directory);
    }

    @Override
    public void addFile(RemoteFile file) {
        this.remoteFiles.add(file);
    }

    @Override
    public List<RemoteDirectory> getDirectories() {
        return this.remoteDirectories;
    }

    @Override
    public List<RemoteFile> getFiles() {
        return this.remoteFiles;
    }
}
