package org.example.core.repository;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractRepositoryFile<P> implements RepositoryElement<P> {
    private final String name;
    private final P path;
    private final AbstractFileReader<P> reader;

    public AbstractRepositoryFile(final String name, final P path, final AbstractFileReader<P> reader) {
        this.name = Objects.requireNonNull(name);
        this.path = Objects.requireNonNull(path);
        this.reader = Objects.requireNonNull(reader);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public P getPath() {
        return this.path;
    }

    public String getContent() {
        return this.reader.getContent(this.path);
    }

    @Override
    public String toString() {
        return "RemoteFileImpl{" +
                "name='" + this.getName() + '\'' +
                ", url='" + this.getPath() + '\'' +
                '}';
    }
}
