package org.example.core.scraper;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.RepositoryDirectory;
import org.example.core.repository.RepositoryFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractScraper<P, X extends RepositoryDirectory<P, Y>, Y extends RepositoryFile<P>, Z extends RepositoryCollection<P, X, Y>> {
    abstract public AbstractRepository<P, X, Y> getRepository(final P repositoryPath, final Optional<String> inputToken);

    protected void buildRepository(final P path, final AbstractRepository<P, X, Y> repository, final Optional<String> inputToken) {
        final Set<String> seen = new HashSet<>();
        this.readFromPath(path, inputToken).ifPresent(collection -> {
            collection.getFiles().forEach(repository::addFile);
            collection.getDirectories().forEach(directory -> {
                buildDirectory(directory, inputToken, seen);
                repository.addDirectory(directory);
            });
        });
    }
    protected void buildDirectory(@NotNull final  RepositoryDirectory<P, Y> directory, @NotNull final Optional<String> token, @NotNull final Set<String> seen) {
        if (seen.contains(directory.getPath().toString()) || Objects.isNull(directory.getPath())) {
            return;
        }
        seen.add(directory.getPath().toString());
        this.sleep();
        this.readFromPath(directory.getPath(), token).ifPresent(collection -> {
            collection.getFiles().forEach(directory::addFile);
            collection.getDirectories().forEach(innerDirectory -> {
                this.buildDirectory(innerDirectory, token, seen);
                directory.addDirectory(innerDirectory);
            });
        });
    }

    protected void sleep() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
    }

    protected abstract Optional<Z> readFromPath(final P path, final Optional<String> token);
}
