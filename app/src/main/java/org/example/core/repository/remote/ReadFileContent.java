package org.example.core.repository.remote;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.scraper.remote.HtmlScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ReadFileContent {
    private final static OkHttpClient client = new OkHttpClient();
    private ReadFileContent(){}

    public static String getContent(final URL path) {
        final String token = System.getenv("GITHUB_TOKEN");
        final Request request = new Request.Builder()
                .url(path)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            try (final Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && Objects.nonNull(response.body())) {
                    final Document document = Jsoup.parse(response.body().string());
                    document.getAllElements().stream()
                            .filter(element -> element.id().contains("LC"))
                            .map(Element::text)
                            .forEach(System.out::println);
                }
            }
        } catch (IOException ignored) {}
        return "";
    }
}
