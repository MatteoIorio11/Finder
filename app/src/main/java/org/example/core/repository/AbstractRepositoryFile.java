package org.example.core.repository;

import java.util.Objects;

public abstract class AbstractRepositoryFile<P> implements RepositoryElement<P> {
    private final String name;
    private final P path;

    public AbstractRepositoryFile(final String name, final P path) {
        this.name = Objects.requireNonNull(name);
        this.path = Objects.requireNonNull(path);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public P getPath() {
        return this.path;
    }

    public abstract String getContent();

    @Override
    public String toString() {
        return "RemoteFileImpl{" +
                "name='" + this.getName() + '\'' +
                ", url='" + this.getPath() + '\'' +
                '}';
    }
}
