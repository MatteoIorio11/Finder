package org.example.core.repository.remote;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.repository.AbstractFileReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RemoteFileReaderImpl extends AbstractFileReader<URL> {
    private final static OkHttpClient client = new OkHttpClient();
    public RemoteFileReaderImpl(){}

    public void checkURL(final URL url) {
        if(Objects.isNull(url)) {
            throw new IllegalArgumentException("The URL cannot be null");
        }
        try {
            url.openConnection().connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getContent(final URL path) {
        final Optional<String> token = Optional.ofNullable(System.getProperty("GITHUB_TOKEN"));
        if(token.isEmpty()) {
            throw new IllegalArgumentException("The GITHUB_TOKEN environment variable is not set");
        }
        final Request request = new Request.Builder()
                .url(path)
                .header("Authorization", "token " + token.get())
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            try (final Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && Objects.nonNull(response.body())) {
                    final Document document = Jsoup.parse(response.body().string());
                    return document.getAllElements().stream()
                            .filter(element -> element.id().contains("LC"))
                            .map(Element::wholeText)
                            .toList();
                }
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
        return List.of();
    }
}
