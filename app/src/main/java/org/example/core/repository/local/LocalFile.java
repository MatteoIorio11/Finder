package org.example.core.repository.local;

import org.example.core.repository.RepositoryFile;

import java.nio.file.Path;

public interface LocalFile extends LocalElement, RepositoryFile<Path> {
}
