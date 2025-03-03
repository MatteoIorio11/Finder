package org.example.core.scraper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.remote.RemoteDirectory;
import org.example.core.remote.RemoteDirectoryImpl;
import org.example.core.remote.Repository;
import org.example.core.remote.RepositoryImpl;
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
        final Repository repository = new RepositoryImpl();
        final Request request = new Request.Builder()
                .url(this.repositoryUrl)
                .header("Authorization", "token " + this.token)
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                final Document document = this.parseHtml(response.body().string());
                document.stream().filter(element -> element.id().contains("folder-row-"));// Get each folder and file.  From a.aria-label I can know if it is file or dir
            } else {
                System.out.println("Failed: " + response.code());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private RemoteDirectory buildDirectory(final Document document) {
        if (Objects.isNull(document)) {
            return null;
        }
        final RemoteDirectory directory = new RemoteDirectoryImpl();
        document.stream().filter(element -> element.id().contains("folder-row-")).forEach(element -> {
            final String name = element.select("a").text();
        });

        return null;
    }

    private Document parseHtml(final String html) {
        return Jsoup.parse(html);
    }
}
