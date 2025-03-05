package org.example.core.scraper.remote;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.remote.*;
import org.example.core.scraper.AbstractScraper;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RemoteScraperImpl extends AbstractScraper<URL, RemoteDirectory, RemoteFile, RemoteRepository, RemoteCollection> {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public RemoteRepository getRepository(final URL repositoryPath, final Optional<String> inputToken) {
        if (inputToken.isEmpty()) {
            throw new IllegalArgumentException("Token is required to access the repository");
        }
        final RemoteRepository repository = new RemoteRepositoryImpl();
        this.buildRepository(repositoryPath, repository, inputToken);
        return repository;
    }

    @Override
    protected Optional<RemoteCollection> readFromPath(URL path, Optional<String> token) {
        if(token.isEmpty()) {
            return Optional.empty();
        }
        final Request request = new Request.Builder()
                .url(path)
                .header("Authorization", "token " + token.get())
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            try (final Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && Objects.nonNull(response.body())) {
                    return HtmlScraper.scrape(response.body().string());
                }
            }
        } catch (IOException ignored) {}
        return Optional.empty();
    }
}
