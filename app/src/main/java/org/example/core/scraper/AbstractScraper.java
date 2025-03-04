package org.example.core.scraper;

import org.example.core.repository.Repository;
import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.RepositoryDirectory;
import org.example.core.repository.RepositoryFile;
import org.example.core.scraper.remote.HtmlScraper;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractScraper<P, X extends RepositoryDirectory<P, Y>, Y extends RepositoryFile<P>, T extends Repository<P, X, Y>, Z extends RepositoryCollection<P, X, Y>> {
    abstract public T getRepository(final P repositoryPath, final Optional<String> inputToken);
    protected void buildDirectory(final  RepositoryDirectory<P, Y> directory, final Optional<String> token, final Set<String> seen) {
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
