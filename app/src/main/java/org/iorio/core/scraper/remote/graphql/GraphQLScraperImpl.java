package org.iorio.core.scraper.remote.graphql;

import com.squareup.okhttp.*;
import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.graphql.*;
import org.iorio.core.scraper.AbstractScraper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GraphQLScraperImpl extends AbstractScraper<String, AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>, AbstractRepositoryFile<String>, RemoteCollectionQL> {
    public final static String GRAPHQL_ENDPOINT = "https://api.github.com/graphql";
    private final OkHttpClient client = new OkHttpClient();
    private final String owner, branch;
    /**
     * Constructor for AbstractScraper
     *
     * @param needSleep whether the scraper needs to sleep
     */
    public GraphQLScraperImpl(final boolean needSleep, final String owner, final String branch) {
        super(needSleep);
        this.owner = owner;
        this.branch = branch;
    }

    @Override
    public AbstractRepository<String, AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>, AbstractRepositoryFile<String>> getRepository(String repositoryName, String repositoryPath, Optional<String> inputToken) {
        final AbstractRepository<String, AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>, AbstractRepositoryFile<String>> repository = new RemoteRepositoryQLImpl(repositoryName, repositoryPath);
        this.buildRepository(repositoryPath, repository, inputToken);
        return repository;
    }

    private String prepareQuery(final String path, final String repositoryName) {
        return String.format("""
            query {
              repository(owner: "%s", name: "%s") {
                object(expression: "%s:%s") {
                  ... on Tree {
                    entries {
                      name
                      type
                      object {
                        ... on Blob {
                          text
                        }
                        ... on Tree {
                          entries {
                            name
                            type
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
        """, this.owner, repositoryName, this.branch, path);
    }
    @Override
    protected Optional<RemoteCollectionQL> readFromPath(String repositoryName, String path, Optional<String> token) {
        final String query = prepareQuery(path, repositoryName);
        final RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                new JSONObject().put("query", query).toString());
        final List<AbstractRepositoryFile<String>> files = new LinkedList<>();
        final List<AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>>
                directories = new LinkedList<>();
        final Request request = new Request.Builder()
                .url(GRAPHQL_ENDPOINT)
                .header("Authorization", "Bearer " + token.get())
                .post(body)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                final var json = new JSONObject(response.body().string());
                json.getJSONObject("data")
                        .getJSONObject("repository")
                        .getJSONObject("object")
                        .getJSONArray("entries")
                        .forEach(entry -> {
                            final var entryJson = (JSONObject) entry;
                            final var name = entryJson.getString("name");
                            final var type = entryJson.getString("type");
                            final String entryPath = path.isEmpty() ? name : path + "/" + name;
                            final var objectName = repositoryName + "/" + entryPath;
                            if (Objects.equals(type, "blob")) {
                                files.add(new RemoteFileQLImpl(objectName, entryPath));
                            }else{
                                directories.add(new RemoteDirectoryQLImpl(objectName, entryPath));
                            }
                        });
                return Optional.of(new RemoteCollectionQL(files, directories));
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return Optional.empty();
    }
}
