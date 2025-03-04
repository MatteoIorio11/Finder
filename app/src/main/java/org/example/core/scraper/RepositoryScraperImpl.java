package org.example.core.scraper;

import org.example.core.remote.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Safelist;
import org.w3c.tidy.Tidy;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RepositoryScraperImpl {
    private final static String GITHUB = "https://github.com";

    static public Optional<RemoteCollection> scrape(final String htmlPage) {
        try {
            final Document document = Jsoup.parse(htmlPage, Parser.htmlParser());
            final List<RemoteFile> files = new LinkedList<>();
            final List<RemoteDirectory<RemoteFile>> directories = new LinkedList<>();
            document.stream().filter(element -> element.id().contains("folder-row-"))
                    .map(element -> element.selectFirst("a"))
                    .filter(element -> !(element.text().equals("..") || element.text().equals(".")))
                    .filter(element -> element.hasAttr("aria-label"))
                    .forEach(aTag -> {
                        final String newPath = GITHUB + aTag.attribute("href").getValue();
                        if (aTag.attr("aria-label").contains("File")) {
                            files.add(new RemoteFileImpl(aTag.text(), newPath));
                        } else {
                            directories.add(new RemoteDirectoryImpl(aTag.text(), newPath));
                        }
                    });
            return Optional.of(new RemoteCollection(files, directories));
        }catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
