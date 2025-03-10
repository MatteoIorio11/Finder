package org.iorio.core.repository;

import org.iorio.core.scraper.local.LocalScraperImpl;
import org.iorio.core.scraper.remote.RemoteScraperImpl;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
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
        if(Objects.isNull(repositoryUrl) || Objects.isNull(token) || Objects.isNull(repoName)) {
            final String message =
                    "The repository " + (Objects.isNull(repositoryUrl) ? "URL" : Objects.isNull(token) ? "token" : "name") + " cannot be null";
            throw new IllegalArgumentException(message);
        }
        try {
            final URLConnection myURLConnection = repositoryUrl.openConnection();
            myURLConnection.connect();
            return new RemoteScraperImpl().getRepository(
                    Objects.requireNonNull(repoName),
                    Objects.requireNonNull(repositoryUrl),
                    Optional.of(Objects.requireNonNull(token)));
        } catch (IOException e) {
            throw new IllegalArgumentException("The repository URL is invalid");
        }
    }

    /**
     * Create a local repository.
     *
     * @param repoName the name of the repository
     * @param repositoryPath the path of the repository
     * @return the repository
     */
    public static AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository(final String repoName, final Path repositoryPath) {
        if(Objects.isNull(repositoryPath) || !Files.exists(repositoryPath)) {
            throw new IllegalArgumentException("The repository path cannot be null or the path does not exist");
        }
        return new LocalScraperImpl().getRepository(repoName, repositoryPath, Optional.empty());
    }
}
