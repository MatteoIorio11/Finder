package org.example.core.repository.local;

import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LocalDirectoryImpl extends AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> implements LocalElement{
    public LocalDirectoryImpl(final String name, final Path path) {
        super(name, path);
    }
}
