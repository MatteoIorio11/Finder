package org.example.core.repository;

import org.example.core.remote.RemoteDirectory;
import org.example.core.remote.RemoteFile;

import java.util.LinkedList;
import java.util.List;

public class RemoteRepositoryImpl implements Repository<RemoteDirectory, RemoteFile> {

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
