package org.example.core.scraper;

import org.example.core.remote.RemoteCollection;

import java.util.Optional;

public interface RepositoryScraper {
    Optional<RemoteCollection> scrape(String htmlPage);
}
