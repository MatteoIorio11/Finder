package org.example.core.scraper;

import org.example.core.remote.Repository;

public interface RepositoryScraper {
    Repository scrape(String user, String repository, String accessToken);
}
