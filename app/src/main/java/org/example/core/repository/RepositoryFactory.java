package org.example.core.repository;

import org.example.core.repository.local.LocalRepositoryImpl;
import org.example.core.scraper.local.LocalScraperImpl;
import org.example.core.scraper.remote.RemoteScraperImpl;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class RepositoryFactory {
    public static AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository(final String repoName, final URL repositoryUrl, final String token) {
        return new RemoteScraperImpl().getRepository(repoName, Objects.requireNonNull(repositoryUrl), Optional.of(Objects.requireNonNull(token)));
    }

    public static AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository(final String repoName, final Path repositoryPath) {
        return new LocalScraperImpl().getRepository(repoName, repositoryPath, Optional.empty());
    }
}
