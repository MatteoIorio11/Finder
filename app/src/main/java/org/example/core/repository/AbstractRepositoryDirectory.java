package org.example.core.repository;

import java.util.*;

public abstract class AbstractRepositoryDirectory<P, Y extends AbstractRepositoryFile<P>> implements RepositoryElement<P> {
    private final Map<String, AbstractRepositoryDirectory<P, Y>> directories;
    private final Map<String, Y> files;
    private final String name;
    private final P remoteUrl;

    public AbstractRepositoryDirectory(final String name, final P remoteUrl) {
        this.directories = new HashMap<>();
        this.files = new HashMap<>();
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
        return this.files.values().stream().toList();
    }
    public List<AbstractRepositoryDirectory<P, Y>> getInnerDirectories(){
        return this.directories.values().stream().toList();
    }
    public void addFile(Y file) {
        this.files.put(file.getName(), file);
    }
    public void addDirectory(AbstractRepositoryDirectory<P, Y> directory) {
        this.directories.put(directory.getName(), directory);
    }

    public boolean hasFile(final String name) {
        return this.files.containsKey(name);
    }
    public boolean hasDirectory(final String name) {
        return this.directories.containsKey(name);
    }

    public Optional<Y> getFile(final String name) {
        return Optional.ofNullable(this.files.get(name));
    }

    public Optional<AbstractRepositoryDirectory<P, Y>> getDirectory(final String name) {
        return Optional.ofNullable(this.directories.get(name));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractRepositoryDirectory && this.name.equals(((AbstractRepositoryDirectory<?, ?>) obj).name);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "name='" + name + '\'' +
                ", remoteUrl='" + remoteUrl + '\'' +
                '}';
    }
}
