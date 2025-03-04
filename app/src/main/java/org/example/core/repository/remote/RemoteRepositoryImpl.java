package org.example.core.repository.remote;

import org.example.core.repository.Repository;

import java.util.LinkedList;
import java.util.List;

public class RemoteRepositoryImpl implements Repository<RemoteDirectory<RemoteFile>, RemoteFile> {

    private final List<RemoteDirectory<RemoteFile>> remoteDirectories;
    private final List<RemoteFile> remoteFiles;

    public RemoteRepositoryImpl() {
        this.remoteDirectories = new LinkedList<>();
        this.remoteFiles = new LinkedList<>();
    }
    @Override
    public void addDirectory(final RemoteDirectory<RemoteFile> directory) {
        this.remoteDirectories.add(directory);
    }

    @Override
    public void addFile(final RemoteFile file) {
        this.remoteFiles.add(file);
    }

    @Override
    public List<RemoteDirectory<RemoteFile>> getDirectories() {
        return this.remoteDirectories;
    }

    @Override
    public List<RemoteFile> getFiles() {
        return this.remoteFiles;
    }
}
