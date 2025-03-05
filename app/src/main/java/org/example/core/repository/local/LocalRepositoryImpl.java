package org.example.core.repository.local;

import org.example.core.repository.AbstractRepository;

import java.nio.file.Path;

public class LocalRepositoryImpl extends AbstractRepository<Path, LocalDirectory, LocalFile> {
    public LocalRepositoryImpl() {
        super();
    }
}
