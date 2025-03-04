package org.example.core.repository.local;

import java.nio.file.Path;
import java.util.Objects;

public class LocalFileImpl implements LocalFile{
    private final String name;
    private final Path localPath;
    public LocalFileImpl(final String name, final Path path) {
        this.name = Objects.requireNonNull(name);
        this.localPath = Objects.requireNonNull(path);
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Path getPath() {
        return this.localPath;
    }

    @Override
    public String getContent() {
        // Read lazily
        return "";
    }
}
