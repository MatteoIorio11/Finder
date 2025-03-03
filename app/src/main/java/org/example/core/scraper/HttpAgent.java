package org.example.core.scraper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.remote.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class HttpAgent {
    private final static String GITHUB = "https://github.com";
    private final URL repositoryUrl;
    private final String token;
    private final OkHttpClient client = new OkHttpClient();

    private final Repository repository = new RepositoryImpl();
    public HttpAgent(final URL repositoryUrl, final String token) {
        this.repositoryUrl = Objects.requireNonNull(repositoryUrl);
        this.token = Objects.requireNonNull(token);
    }

    public Repository getRepository() {
        this.makeRequest(this.repositoryUrl.toString())
                .ifPresent(document -> {
                    document.stream().filter(element -> element.id().contains("folder-row-")).forEach(element -> {
                        // different element that are contained inside a Repo
                        final Element aTag = element.selectFirst("a");
                        if (aTag.attr("aria-label").contains("File")) {
                            this.repository.addFile(new RemoteFileImpl(aTag.html()));
                        } else {
                            // create new document
                            final String newPath = GITHUB + "/" + aTag.attribute("href").getValue();
                                this.makeRequest(newPath).ifPresent(newDocument -> {
                                    final RemoteDirectory directory = this.buildDirectory(newDocument);
                                    System.out.println(directory.getName());
                                    this.repository.addDirectory(directory);
                                });
                        }
                    });
                });

        System.out.println(this.repository.getRemoteDirectories());
        return null;
    }

    private RemoteDirectory buildDirectory(final Document document) {
        if (Objects.isNull(document)) {
            return null;
        }
        final RemoteDirectory directory = new RemoteDirectoryImpl(document.title());
        document.stream().filter(element -> element.id().contains("folder-row-")).forEach(element -> {
            final Element aTag = element.selectFirst("a");
            if (aTag.attr("aria-label").contains("File")) {
                directory.addFile(new RemoteFileImpl(aTag.html()));
            } else {
                // create new document
                final String newPath = GITHUB + "/" + aTag.attribute("href").getValue();
                this.makeRequest(newPath).ifPresent(newDocument -> {
                    directory.addDirectory(directory);
                });
            }
        });
        return directory;
    }

    private Optional<Document> makeRequest(final String path) {
        final Request request = new Request.Builder()
                .url(path)
                .header("Authorization", "token " + this.token)
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return Optional.of(this.parseHtml(response.body().string()));
            } else {
                System.out.println("Failed: " + response.code());
            }
        } catch (IOException ex) {}
        return Optional.empty();
    }

    private Document parseHtml(final String html) {
        return Jsoup.parse(html);
    }
}
