package org.example.core.scraper;

import org.example.core.remote.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RepositoryScraperImpl implements RepositoryScraper {
    private final static String GITHUB = "https://github.com";
    @Override
    public RemoteCollection scrape(final String htmlPage) {
        final Document document = Jsoup.parse(htmlPage);
        final List<RemoteFile> files = new LinkedList<>();
        final List<RemoteDirectory> directories = new LinkedList<>();
        document.stream().filter(element -> element.id().contains("folder-row-"))
                .map(element -> element.selectFirst("a"))
                .filter(element -> !(element.text().equals("..") || element.text().equals(".")))
                .filter(element -> element.hasAttr("aria-label"))
                .forEach(aTag-> {
                    final String newPath = GITHUB + aTag.attribute("href").getValue();
                    if (aTag.attr("aria-label").contains("File")) {
                        files.add(new RemoteFileImpl(aTag.text(), newPath));
                    } else {
                        directories.add(new RemoteDirectoryImpl(aTag.text(), newPath));
                    }
                });
        return new RemoteCollection(files, directories);
    }
}
