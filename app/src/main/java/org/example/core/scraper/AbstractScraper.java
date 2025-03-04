package org.example.core.scraper;

import org.example.core.repository.Repository;
import org.example.core.repository.RepositoryDirectory;
import org.example.core.repository.RepositoryFile;

import java.util.Optional;

public abstract class AbstractScraper<P, X extends RepositoryDirectory<P, Y>, Y extends RepositoryFile<P>, T extends Repository<P, X, Y>> {
    abstract public T getRepository(final P repositoryUrl, final Optional<String> inputToken);
}
