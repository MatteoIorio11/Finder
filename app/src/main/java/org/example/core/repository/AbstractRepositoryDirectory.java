package org.example.core.repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractRepositoryDirectory<P, Y extends AbstractRepositoryFile<P>> implements RepositoryElement<P> {
    private final List<AbstractRepositoryDirectory<P, Y>> directories;
    private final List<Y> files;
    private final String name;
    private final P remoteUrl;

    public AbstractRepositoryDirectory(final String name, final P remoteUrl) {
        this.directories = new LinkedList<>();
        this.files = new LinkedList<>();
        this.name = Objects.requireNonNull(name);
        this.remoteUrl = Objects.requireNonNull(remoteUrl);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public P getPath() {
        return remoteUrl;
    }

    public List<Y> getFiles() {
        return Collections.unmodifiableList(this.files);
    }
    public List<AbstractRepositoryDirectory<P, Y>> getInnerDirectories(){
        return Collections.unmodifiableList(this.directories);
    }
    public void addFile(Y file) {
        this.files.add(file);
    }
    public void addDirectory(AbstractRepositoryDirectory<P, Y> directory) {
        this.directories.add(directory);
    }

    @Override
    public String toString() {
        return "RemoteDirectoryImpl{" +
                "name='" + name + '\'' +
                ", remoteUrl='" + remoteUrl + '\'' +
                '}';
    }
}
