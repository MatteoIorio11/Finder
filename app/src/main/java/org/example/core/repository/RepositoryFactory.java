package org.example.core.repository;

import org.example.core.repository.local.LocalRepository;
import org.example.core.repository.remote.RemoteRepository;
import org.example.core.scraper.remote.HttpAgent;

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

public class RepositoryFactory {
    public static RemoteRepository remoteRepository(final URL repositoryUrl, final String token) {
        return HttpAgent.getRemoteRepository(Objects.requireNonNull(repositoryUrl), Objects.requireNonNull(token));
    }

    public static LocalRepository localRepository(final Path repositoryPath) {
//        return HttpAgent.getRemoteRepository(Objects.requireNonNull(repositoryUrl));
        return null;
    }
}
