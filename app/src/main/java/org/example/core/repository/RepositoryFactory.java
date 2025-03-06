package org.example.core.repository;

import org.example.core.scraper.local.LocalScraperImpl;
import org.example.core.scraper.remote.RemoteScraperImpl;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * A factory for creating repositories.
 */
public class RepositoryFactory {
    /**
     * Create a remote repository.
     *
     * @param repoName the name of the repository
     * @param repositoryUrl the URL of the repository
     * @param token the token for the repository
     * @return the repository
     */
    public static AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository(final String repoName, final URL repositoryUrl, final String token) {
        return new RemoteScraperImpl().getRepository(repoName, Objects.requireNonNull(repositoryUrl), Optional.of(Objects.requireNonNull(token)));
    }

    /**
     * Create a local repository.
     *
     * @param repoName the name of the repository
     * @param repositoryPath the path of the repository
     * @return the repository
     */
    public static AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository(final String repoName, final Path repositoryPath) {
        return new LocalScraperImpl().getRepository(repoName, repositoryPath, Optional.empty());
    }
}
