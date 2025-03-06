package org.example.core.scraper.remote;

import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.remote.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A scraper for HTML pages
 */
public class HtmlScraper {
    private final static String GITHUB = "https://github.com";
    private final static Logger logger = LoggerFactory.getLogger(HtmlScraper.class);

    /**
     * Scrape the HTML page
     * @param htmlPage the HTML page
     * @return the collection of files and directories
     */
    static public Optional<RemoteCollection> scrape(final String htmlPage) {
        try {
            logger.info("Scraping HTML page");
            final Document document = Jsoup.parse(htmlPage, Parser.htmlParser());
            final List<AbstractRepositoryFile<URL>> files = new LinkedList<>();
            final List<AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>> directories = new LinkedList<>();
            document.stream().filter(element -> element.id().contains("folder-row-"))
                    .map(element -> element.selectFirst("a"))
                    .filter(element -> !(element.text().equals("..") || element.text().equals(".")))
                    .filter(element -> element.hasAttr("aria-label"))
                    .forEach(aTag -> {
                        final String newPath = GITHUB + aTag.attribute("href").getValue();
                        final String uniqueName = buildName(aTag.attribute("href").getValue());
                        try {
                            if (aTag.attr("aria-label").contains("File")) {
                                files.add(new RemoteFileImpl(uniqueName, URI.create(newPath).toURL()));
                            } else {
                                directories.add(new RemoteDirectoryImpl(uniqueName, URI.create(newPath).toURL()));
                            }
                        }catch (Exception ignored) {
                            logger.error("Failed to create file or directory");
                        }
                    });
            return Optional.of(new RemoteCollection(files, directories));
        }catch (Exception ignored) {
            logger.error("Failed to scrape HTML page");
            return Optional.empty();
        }
    }

    private static String buildName(final String path) {
        final String partialName = Arrays.stream(path.split("/"))
                .skip(2)
                .filter(dir -> !(dir.equals("blob") || dir.equals("tree")))
                .collect(Collectors.joining("/"));
        final String[] components = partialName.split("/");
        return Stream.concat(Stream.of(components[0]), Arrays.stream(components).skip(2)).collect(Collectors.joining("/"));
    }
}
