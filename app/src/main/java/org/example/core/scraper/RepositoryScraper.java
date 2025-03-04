package org.example.core.scraper;

import org.example.core.remote.RemoteCollection;
import org.example.core.remote.Repository;

public interface RepositoryScraper {
    RemoteCollection scrape(String htmlPage);
}
