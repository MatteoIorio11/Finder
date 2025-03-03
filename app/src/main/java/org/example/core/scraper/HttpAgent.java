package org.example.core.scraper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.remote.Repository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HttpAgent {
    private final URL repositoryUrl;
    private final String token;
    private final OkHttpClient client = new OkHttpClient();
    public HttpAgent(final URL repositoryUrl, final String token) {
        this.repositoryUrl = Objects.requireNonNull(repositoryUrl);
        this.token = Objects.requireNonNull(token);
    }

    public Repository getRepository() {
        Request request = new Request.Builder()
                .url(this.repositoryUrl)
                .header("Authorization", "token " + this.token)
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                final Document = this.parseHtml(response.body().string());
            } else {
                System.out.println("Failed: " + response.code());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private Document parseHtml(final String html) {
        return Jsoup.parse(html);
    }
}
