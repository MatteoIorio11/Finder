package org.example.core.scraper.remote;

import com.squareup.okhttp.*;
import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.remote.*;
import org.example.core.scraper.AbstractScraper;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * A remote scraper implementation
 */
public class RemoteScraperImpl extends AbstractScraper<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>, RemoteCollection> {
    private final OkHttpClient client = new OkHttpClient();

    public RemoteScraperImpl() {
        super(true);
    }

    @Override
    public AbstractRepository<URL,  AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> getRepository(final String repositoryName, final URL repositoryPath, final Optional<String> inputToken) {
        logger.info("Creating repository: " + repositoryName);
        if (inputToken.isEmpty()) {
            throw new IllegalArgumentException("Token is required to access the repository");
        }
        final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> repository = new RemoteRepositoryImpl(repositoryName, repositoryPath);
        this.buildRepository(repositoryPath, repository, inputToken);
        return repository;
    }

    @Override
    protected Optional<RemoteCollection> readFromPath(final String repositoryName, final URL path, final Optional<String> token) {
        logger.info("Reading from path: " + path);
        if(token.isEmpty()) {
            return Optional.empty();
        }
        final Request request = new Request.Builder()
                .url(path)
                .header("Authorization", "token " + token.get())
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return HtmlScraper.scrape(response.body().string());
            }
        } catch (IOException ignored) {}
        return Optional.empty();
    }
}
