package org.example.core.remote;

import java.util.LinkedList;
import java.util.List;

public class RepositoryImpl implements Repository{

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
    public List<RemoteDirectory> getRemoteDirectories() {
        return this.remoteDirectories;
    }

    @Override
    public List<RemoteFile> getRemoteFiles() {
        return this.remoteFiles;
    }
}
