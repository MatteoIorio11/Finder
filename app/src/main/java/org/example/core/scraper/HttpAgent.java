package org.example.core.scraper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.remote.*;
import org.example.core.repository.Repository;
import org.example.core.remote.RemoteRepositoryImpl;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HttpAgent {
    private static final OkHttpClient client = new OkHttpClient();
    private HttpAgent() {
    }

    static public Repository<RemoteDirectory<RemoteFile>, RemoteFile> getRemoteRepository(final URL repositoryUrl, final String token) {
        final Repository<RemoteDirectory<RemoteFile>, RemoteFile> repository = new RemoteRepositoryImpl();
        final Set<String> seen = new HashSet<>();
        makeRequest(repositoryUrl.toString(), token).flatMap(RepositoryScraperImpl::scrape).ifPresent(collection -> {
            collection.files().forEach(repository::addFile);
            collection.directories().forEach(directory -> {
                buildDirectory(directory, token, seen);
                repository.addDirectory(directory);
            });
        });
        return repository;
    }

    static private void buildDirectory(final RemoteDirectory<RemoteFile> directory, final String token, final Set<String> seen) {
        if (seen.contains(directory.getRemoteUrl())) {
            return;
        }
        seen.add(directory.getRemoteUrl());
        sleep();
        makeRequest(directory.getRemoteUrl(), token).flatMap(RepositoryScraperImpl::scrape).ifPresent(collection -> {
            collection.files().forEach(directory::addFile);
            collection.directories().forEach(innerDirectory -> {
                buildDirectory(innerDirectory, token, seen);
                directory.addDirectory(innerDirectory);
            });
        });
    }

    static void sleep() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
    }

    static private Optional<String> makeRequest(final String path, final String token) {
        final Request request = new Request.Builder()
                .url(path)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            try (final Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && Objects.nonNull(response.body())) {
                    return Optional.of(response.body().string());
                }
            }
        } catch (IOException ignored) {}
        return Optional.empty();
    }
}
