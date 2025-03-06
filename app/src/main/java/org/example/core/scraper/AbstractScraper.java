package org.example.core.scraper;

import org.checkerframework.checker.units.qual.A;
import org.example.core.repository.AbstractRepository;
import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * An abstract scraper
 * @param <P> the type of the path
 * @param <X> the type of the directory
 * @param <Y> the type of the file
 * @param <Z> the type of the collection
 */
public abstract class AbstractScraper<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>, Z extends RepositoryCollection<P, X, Y>> {
    private final boolean needSleep;
    /**
     * Constructor for AbstractScraper
     * @param needSleep whether the scraper needs to sleep
     */
    protected AbstractScraper(final boolean needSleep) {
        this.needSleep = needSleep;
    }
    /**
     * Get the repository
     * @param repositoryName the name of the repository
     * @param repositoryPath the path of the repository
     * @param inputToken the input token
     * @return the repository
     */
    abstract public AbstractRepository<P, X, Y> getRepository(final String repositoryName, final P repositoryPath, final Optional<String> inputToken);

    /**
     * Build the repository recursively using the readFromPath method
     * @param path the path to build the repository from
     * @param repository the repository to build
     * @param inputToken the input token
     */
    protected void buildRepository(final P path, final AbstractRepository<P, X, Y> repository, final Optional<String> inputToken) {
        final Set<String> seen = new HashSet<>();
        this.readFromPath(repository.getName(), path, inputToken).ifPresent(collection -> {
            collection.getFiles().forEach(repository::addFile);
            collection.getDirectories().forEach(directory -> {
                buildDirectory(repository.getName(), directory, inputToken, seen);
                repository.addDirectory(directory);
            });
        });
    }

    /**
     * Build a directory recursively using the readFromPath method
     * @param repositoryName the name of the repository
     * @param directory the directory to build
     * @param token the token
     * @param seen the set of seen paths
     */
    protected void buildDirectory(@NotNull final String repositoryName, @NotNull final AbstractRepositoryDirectory<P, Y> directory, @NotNull final Optional<String> token, @NotNull final Set<String> seen) {
        if (seen.contains(directory.getPath().toString()) || Objects.isNull(directory.getPath())) {
            return;
        }
        seen.add(directory.getPath().toString());
        if(this.needSleep) {this.sleep();}
        this.readFromPath(repositoryName, directory.getPath(), token).ifPresent(collection -> {
            collection.getFiles().forEach(directory::addFile);
            collection.getDirectories().forEach(innerDirectory -> {
                this.buildDirectory(repositoryName, innerDirectory, token, seen);
                directory.addDirectory(innerDirectory);
            });
        });
    }

    /**
     * Sleep for 2 seconds
     */
    protected void sleep() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
    }

    /**
     * Read from a path (the path could be local or remote)
     * @param repositoryName the name of the repository
     * @param path the path to read from
     * @param token the token
     * @return the collection
     */
    protected abstract Optional<Z> readFromPath(final String repositoryName, final P path, final Optional<String> token);
}
