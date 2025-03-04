package org.example.core.repository.remote;


import org.example.core.repository.RepositoryDirectory;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RemoteDirectoryImpl implements RemoteDirectory {
    private final String name;
    private final URL remoteUrl;
    private final List<RemoteFile> remoteFiles;
    private final List<RepositoryDirectory<URL, RemoteFile>> remoteDirectories;
    public RemoteDirectoryImpl(final String name, final URL remoteUrl) {
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
    public List<RepositoryDirectory<URL, RemoteFile>> getInnerDirectories() {
        return Collections.unmodifiableList(this.remoteDirectories);
    }

    @Override
    public void addFile(final RemoteFile file) {
        this.remoteFiles.add(file);
    }

    @Override
    public void addDirectory(final RepositoryDirectory<URL, RemoteFile> directory) {
        this.remoteDirectories.add(directory);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public URL getPath() {
        return this.remoteUrl;
    }
}
