package org.example.core.scraper.local;

import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.local.LocalCollection;
import org.example.core.repository.local.LocalDirectory;
import org.example.core.repository.local.LocalFile;
import org.example.core.repository.local.LocalRepository;
import org.example.core.scraper.AbstractScraper;

import java.nio.file.Path;
import java.util.Optional;

public class LocalScraperImpl extends AbstractScraper<Path, LocalDirectory, LocalFile, LocalRepository, LocalCollection> {

    @Override
    public LocalRepository getRepository(final Path repositoryPath, final Optional<String> inputToken) {
        return null;
    }

    @Override
    protected Optional<LocalCollection> readFromPath(Path path, Optional<String> token) {
        return Optional.empty();
    }

}
