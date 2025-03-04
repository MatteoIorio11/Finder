package org.example.core.repository.remote;


import org.example.core.repository.RepositoryDirectory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RemoteDirectoryImpl implements RemoteDirectory {
    private final String name, remoteUrl;
    private final List<RemoteFile> remoteFiles;
    private final List<RepositoryDirectory<String, RemoteFile>> remoteDirectories;
    public RemoteDirectoryImpl(final String name, final String remoteUrl) {
        this.name = Objects.requireNonNull(name);
        this.remoteUrl = Objects.requireNonNull(remoteUrl);
        this.remoteFiles = new LinkedList<>();
        this.remoteDirectories = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "RemoteDirectoryImpl{" +
                "name='" + name + '\'' +
                ", remoteUrl='" + remoteUrl + '\'' +
                '}';
    }

    @Override
    public List<RemoteFile> getFiles() {
        return Collections.unmodifiableList(this.remoteFiles);
    }

    @Override
    public List<RepositoryDirectory<String, RemoteFile>> getInnerDirectories() {
        return Collections.unmodifiableList(this.remoteDirectories);
    }

    @Override
    public void addFile(RemoteFile file) {
        this.remoteFiles.add(file);
    }

    @Override
    public void addDirectory(RepositoryDirectory<String, RemoteFile> directory) {
        this.remoteDirectories.add(directory);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.remoteUrl;
    }
}
