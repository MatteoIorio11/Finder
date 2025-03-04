package org.example.core.scraper.local;

import org.example.core.repository.local.LocalDirectory;
import org.example.core.repository.local.LocalFile;
import org.example.core.repository.local.LocalRepository;
import org.example.core.scraper.AbstractScraper;

import java.nio.file.Path;
import java.util.Optional;

public class LocalScraper extends AbstractScraper<Path, LocalDirectory, LocalFile, LocalRepository> {

    @Override
    public LocalRepository getRepository(final Path repositoryUrl, final Optional<String> inputToken) {
        return null;
    }
}
