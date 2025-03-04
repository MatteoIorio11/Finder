package org.example.core.scraper.remote;

import org.example.core.repository.remote.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class HtmlScraper {
    private final static String GITHUB = "https://github.com";

    static public Optional<RemoteCollection> scrape(final String htmlPage) {
        try {
            final Document document = Jsoup.parse(htmlPage, Parser.htmlParser());
            final List<RemoteFile> files = new LinkedList<>();
            final List<RemoteDirectory> directories = new LinkedList<>();
            document.stream().filter(element -> element.id().contains("folder-row-"))
                    .map(element -> element.selectFirst("a"))
                    .filter(element -> !(element.text().equals("..") || element.text().equals(".")))
                    .filter(element -> element.hasAttr("aria-label"))
                    .forEach(aTag -> {
                        final String newPath = GITHUB + aTag.attribute("href").getValue();
                        try {
                            if (aTag.attr("aria-label").contains("File")) {
                                files.add(new RemoteFileImpl(aTag.text(), URI.create(newPath).toURL()));
                            } else {
                                directories.add(new RemoteDirectoryImpl(aTag.text(), URI.create(newPath).toURL()));
                            }
                        }catch (Exception ignored) {}
                    });
            return Optional.of(new RemoteCollection(files, directories));
        }catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
