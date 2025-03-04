package org.example.core.repository.local;

import org.example.core.repository.Repository;

import java.nio.file.Path;

public interface LocalRepository extends Repository<Path, LocalDirectory, LocalFile> {
}
