package org.example.core.scraper.local;

import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.local.*;
import org.example.core.scraper.AbstractScraper;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LocalScraperImpl extends AbstractScraper<Path, LocalDirectory, LocalFile, LocalRepository, LocalCollection> {

    @Override
    public LocalRepository getRepository(final Path repositoryPath, final Optional<String> inputToken) {
        final LocalRepository repository = new LocalRepositoryImpl();
        this.buildRepository(repositoryPath, repository, inputToken);
        return repository;
    }

    @Override
    protected Optional<LocalCollection> readFromPath(final Path path, final Optional<String> token) {

        return Optional.empty();
    }

}
