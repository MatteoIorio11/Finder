package org.example.core.repository;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public String getContent(@NotNull final AbstractFileReader<P> reader) {
        return reader.getContent(this.getPath());
    }

    @Override
    public String toString() {
        return "RemoteFileImpl{" +
                "name='" + this.getName() + '\'' +
                ", url='" + this.getPath() + '\'' +
                '}';
    }
}
