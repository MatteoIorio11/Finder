package org.example.core.repository.local;

import org.example.core.repository.RepositoryDirectory;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LocalDirectoryImpl implements LocalDirectory{
    private final String name;
    private final Path localPath;
    private final List<LocalFile> files;
    private final List<RepositoryDirectory<Path, LocalFile>> directories;
    public LocalDirectoryImpl(final String name, final Path path) {
        this.name = Objects.requireNonNull(name);
        this.localPath = Objects.requireNonNull(path);
        this.files = new LinkedList<>();
        this.directories = new LinkedList<>();
    }
    @Override
    public List<LocalFile> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    @Override
    public List<RepositoryDirectory<Path, LocalFile>> getInnerDirectories() {
        return Collections.unmodifiableList(this.directories);
    }

    @Override
    public void addFile(final LocalFile file) {
        this.files.add(file);
    }

    @Override
    public void addDirectory(RepositoryDirectory<Path, LocalFile> directory) {
        this.directories.add(directory);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Path getPath() {
        return this.localPath;
    }
}
