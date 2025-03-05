package org.example.core.repository;

import org.example.core.repository.remote.RemoteFile;
import org.example.core.scraper.remote.RemoteScraperImpl;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class RepositoryFactory {
    public static AbstractRepository<URL, RemoteDirectory, RemoteFile> remoteRepository(final URL repositoryUrl, final String token) {
        return new RemoteScraperImpl().getRepository(Objects.requireNonNull(repositoryUrl), Optional.of(Objects.requireNonNull(token)));
    }

    public static void localRepository(final Path repositoryPath) {
//        return HttpAgent.getRemoteRepository(Objects.requireNonNull(repositoryUrl));
        return;
    }
}
