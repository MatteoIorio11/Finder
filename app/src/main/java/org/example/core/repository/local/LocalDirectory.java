package org.example.core.repository.local;

import org.example.core.repository.RepositoryDirectory;

import java.nio.file.Path;

public interface LocalDirectory extends LocalElement, RepositoryDirectory<Path, LocalFile> {
}
