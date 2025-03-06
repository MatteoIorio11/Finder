package org.example.core.scraper.remote;

import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.remote.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HtmlScraper {
    private final static String GITHUB = "https://github.com";

    static public Optional<RemoteCollection> scrape(final String htmlPage) {
        try {
            final Document document = Jsoup.parse(htmlPage, Parser.htmlParser());
            final List<AbstractRepositoryFile<URL>> files = new LinkedList<>();
            final List<AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>> directories = new LinkedList<>();
            document.stream().filter(element -> element.id().contains("folder-row-"))
                    .map(element -> element.selectFirst("a"))
                    .filter(element -> !(element.text().equals("..") || element.text().equals(".")))
                    .filter(element -> element.hasAttr("aria-label"))
                    .forEach(aTag -> {
                        final String newPath = GITHUB + aTag.attribute("href").getValue();
                        final String uniqueName = Arrays.stream(aTag.attribute("href").getValue().split("/")).skip(2).collect(Collectors.joining("/"));
                        try {
                            if (aTag.attr("aria-label").contains("File")) {
                                files.add(new RemoteFileImpl(uniqueName, URI.create(newPath).toURL()));
                            } else {
                                directories.add(new RemoteDirectoryImpl(uniqueName, URI.create(newPath).toURL()));
                            }
                        }catch (Exception ignored) {}
                    });
            return Optional.of(new RemoteCollection(files, directories));
        }catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
