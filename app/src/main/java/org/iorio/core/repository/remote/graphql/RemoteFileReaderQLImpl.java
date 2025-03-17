package org.iorio.core.repository.remote.graphql;

import com.squareup.okhttp.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.iorio.core.repository.FileReader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.iorio.core.scraper.remote.graphql.GraphQLScraperImpl.GRAPHQL_ENDPOINT;

public class RemoteFileReaderQLImpl implements FileReader<String> {
    private final String owner;
    private final String repositoryName;
    private final String branch;
    private final OkHttpClient client = new OkHttpClient();

    public RemoteFileReaderQLImpl(@NonNull String owner, @NonNull String repositoryName, @NonNull String branch) {
        this.owner = owner;
        this.repositoryName = repositoryName;
        this.branch = branch;
    }
    @Override
    public List<String> getContent(@NonNull String path) {
        final String query = String.format("""
            query {
              repository(owner: "%s", name: "%s") {
                object(expression: "%s:%s") {
                  ... on Blob {
                    text
                  }
                }
              }
            }
        """, this.owner, this.repositoryName, this.branch, path);

        final RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                new JSONObject().put("query", query).toString());
        final Request request = new Request.Builder()
                .url(GRAPHQL_ENDPOINT)
                .header("Authorization", "Bearer " + System.getProperty("GITHUB_TOKEN"))
                .post(body)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                final String content = new JSONObject(response.body().string())
                        .getJSONObject("data")
                        .getJSONObject("repository")
                        .getJSONObject("object")
                        .getString("text");
                return List.of(content.split("\n"));
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return List.of();
    }
}
