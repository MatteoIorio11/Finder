package org.example.core.scraper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.remote.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HttpAgent {
    private final OkHttpClient client = new OkHttpClient();
    RepositoryScraper repositoryScraper = new RepositoryScraperImpl();
    public HttpAgent() {
    }

    public Repository getRepository(final URL repositoryUrl, final String token) {
        final Repository repository = new RepositoryImpl();
        final Set<String> seen = new HashSet<>();
        this.makeRequest(repositoryUrl.toString(), token)
                .ifPresent(htmlPage -> {
                    final RemoteCollection collection = this.repositoryScraper.scrape(htmlPage);
                    collection.files().forEach(repository::addFile);
                    collection.directories().forEach(directory -> {
                        this.buildDirectory(directory, token, seen);
                        repository.addDirectory(directory);
                    });
                });
        return repository;
    }

    public void buildDirectory(final RemoteDirectory directory, final String token, final Set<String> seen) {
        System.out.println("Building: " + directory.getRemoteUrl());
        if (seen.contains(directory.getRemoteUrl())) {
            return;
        }
        seen.add(directory.getRemoteUrl());
        this.sleep();
        this.makeRequest(directory.getRemoteUrl(), token).ifPresent(html -> {
            final RemoteCollection remoteCollection = this.repositoryScraper.scrape(html);
            remoteCollection.files().forEach(directory::addFile);
            remoteCollection.directories().forEach(innerDirectory -> {
                this.buildDirectory(innerDirectory, token, seen);
                directory.addDirectory(innerDirectory);
            });
        });
    }

    void sleep() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }

    private Optional<String> makeRequest(final String path, final String token) {
        final Request request = new Request.Builder()
                .url(path)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            try (final Response response = client.newCall(request).execute()) {
                System.out.println("Requesting: " + path + " " + response.code());
                if (response.isSuccessful()) {
                    return Optional.of(response.body().string());
                } else {
                    System.out.println("Failed: " + response.code());
                }
            }
        } catch (IOException ex) {}
        return Optional.empty();
    }
}
