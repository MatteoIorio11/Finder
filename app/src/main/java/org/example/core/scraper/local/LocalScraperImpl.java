package org.example.core.scraper.local;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.local.*;
import org.example.core.scraper.AbstractScraper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class LocalScraperImpl extends AbstractScraper<Path, LocalDirectory, LocalFile, LocalCollection> {

    @Override
    public AbstractRepository<Path, LocalDirectory, LocalFile> getRepository(final Path repositoryPath, final Optional<String> inputToken) {
        final var repository = new LocalRepositoryImpl();
        this.buildRepository(repositoryPath, repository, inputToken);
        return repository;
    }

    @Override
    protected Optional<LocalCollection> readFromPath(final Path path, final Optional<String> token) {
        final File rootDirectory = new File(path.toString());
        if (rootDirectory.exists() && rootDirectory.listFiles() != null) {
            final File[] allFiles = rootDirectory.listFiles();
            if (Objects.nonNull(allFiles)) {
                final List<LocalDirectory> directories = new LinkedList<>();
                final List<LocalFile> files = new LinkedList<>();
                Stream.of(allFiles).forEach(file -> {
                    if (file.isDirectory()) {
                        directories.add(new LocalDirectoryImpl(file.getName(), file.toPath()));
                    } else {
                        files.add(new LocalFileImpl(file.getName(), file.toPath()));
                    }
                });
                return Optional.of(new LocalCollection(files, directories));
            }
        }
        return Optional.empty();
    }

}
