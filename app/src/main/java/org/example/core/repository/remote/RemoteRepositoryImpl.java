package org.example.core.repository.remote;

import org.example.core.repository.Repository;

import java.rmi.Remote;
import java.util.LinkedList;
import java.util.List;

public class RemoteRepositoryImpl implements RemoteRepository {

    private final List<RemoteDirectory> remoteDirectories;
    private final List<RemoteFile> remoteFiles;

    public RemoteRepositoryImpl() {
        this.remoteDirectories = new LinkedList<>();
        this.remoteFiles = new LinkedList<>();
    }
    @Override
    public void addDirectory(final RemoteDirectory directory) {
        this.remoteDirectories.add(directory);
    }

    @Override
    public void addFile(final RemoteFile file) {
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
