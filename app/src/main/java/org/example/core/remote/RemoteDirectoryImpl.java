package org.example.core.remote;


import org.example.core.repository.RepositoryDirectory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RemoteDirectoryImpl implements RemoteDirectory<RemoteFile> {
    private final String name, remoteUrl;
    private final List<RemoteFile> remoteFiles;
    private final List<RepositoryDirectory<RemoteFile>> remoteDirectories;
    public RemoteDirectoryImpl(final String name, final String remoteUrl) {
        this.name = Objects.requireNonNull(name);
        this.remoteUrl = Objects.requireNonNull(remoteUrl);
        this.remoteFiles = new LinkedList<>();
        this.remoteDirectories = new LinkedList<>();
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRemoteUrl() {
        return this.remoteUrl;
    }

    @Override
    public List<RemoteFile> getFiles() {
        return this.remoteFiles;
    }

    @Override
    public List<RepositoryDirectory<RemoteFile>> getInnerDirectories() {
        return this.remoteDirectories;
    }

    @Override
    public void addFile(final RemoteFile file) {
        this.remoteFiles.add(file);
    }

    @Override
    public void addDirectory(RepositoryDirectory<RemoteFile> directory) {
        this.remoteDirectories.add(directory);
    }


    @Override
    public String toString() {
        return "RemoteDirectoryImpl{" +
                "name='" + name + '\'' +
                ", remoteUrl='" + remoteUrl + '\'' +
                '}';
    }
}
