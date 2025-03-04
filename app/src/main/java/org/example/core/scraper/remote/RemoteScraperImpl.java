package org.example.core.scraper.remote;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.repository.remote.RemoteDirectory;
import org.example.core.repository.remote.RemoteFile;
import org.example.core.repository.remote.RemoteRepository;
import org.example.core.repository.remote.RemoteRepositoryImpl;
import org.example.core.scraper.AbstractScraper;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RemoteScraperImpl extends AbstractScraper<URL, RemoteDirectory, RemoteFile, RemoteRepository> {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public RemoteRepository getRepository(final URL repositoryUrl, final Optional<String> inputToken) {
        if (inputToken.isEmpty()) {
            throw new IllegalArgumentException("Token is required to access the repository");
        }
        final String token = inputToken.get();
        final RemoteRepository repository = new RemoteRepositoryImpl();
        final Set<String> seen = new HashSet<>();
        makeRequest(repositoryUrl, token).flatMap(HtmlScraper::scrape).ifPresent(collection -> {
            collection.files().forEach(repository::addFile);
            collection.directories().forEach(directory -> {
                buildDirectory(directory, token, seen);
                repository.addDirectory(directory);
            });
        });
        return repository;
    }

    private void buildDirectory(final RemoteDirectory directory, final String token, final Set<String> seen) {
        if (seen.contains(directory.getPath().toString()) || Objects.isNull(directory.getPath())) {
            return;
        }
        seen.add(directory.getPath().toString());
        this.sleep();
        this.makeRequest(directory.getPath(), token).flatMap(HtmlScraper::scrape).ifPresent(collection -> {
            collection.files().forEach(directory::addFile);
            collection.directories().forEach(innerDirectory -> {
                this.buildDirectory(innerDirectory, token, seen);
                directory.addDirectory(innerDirectory);
            });
        });
    }

    void sleep() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
    }

    private Optional<String> makeRequest(final URL path, final String token) {
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
