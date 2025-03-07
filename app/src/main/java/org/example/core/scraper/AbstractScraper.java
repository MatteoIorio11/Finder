package org.example.core.scraper;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * An abstract scraper
 * @param <P> the type of the path
 * @param <X> the type of the directory
 * @param <Y> the type of the file
 * @param <Z> the type of the collection
 */
public abstract class AbstractScraper<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>, Z extends RepositoryCollection<P, X, Y>> {
    protected final static Logger logger = LoggerFactory.getLogger(AbstractScraper.class);
    private final boolean needSleep;
    /**
     * Constructor for AbstractScraper
     * @param needSleep whether the scraper needs to sleep
     */
    protected AbstractScraper(final boolean needSleep) {
        logger.info("Created scraper with sleep: " + needSleep);
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
        logger.info("Building repository: " + repository.getName());
            final Set<String> seen = new HashSet<>();
        this.readFromPath(repository.getName(), path, inputToken).ifPresent(collection -> {
            collection.getFiles().forEach(repository::addFile);
            collection.getDirectories().forEach(directory -> {
                buildDirectory(repository.getName(), directory, inputToken, seen);
                repository.addDirectory(directory);
            });
        });
        logger.info("Finished building repository: " + repository.getName());
    }

    /**
     * Build a directory recursively using the readFromPath method
     * @param directoryName the name of the repository
     * @param directory the directory to build
     * @param token the token
     */
    protected void buildDirectory(@NotNull final String directoryName, @NotNull final AbstractRepositoryDirectory<P, Y> directory, @NotNull final Optional<String> token, @NotNull final Set<String> seen) {
        if (seen.contains(directory.getPath().toString()) || Objects.isNull(directory.getPath())) {
            return;
        }
        final List<AbstractRepositoryDirectory<P, Y>> directories = new Stack<>();
        directories.add(directory);
        while(!directories.isEmpty()) {
            if(this.needSleep) {this.sleep();}
            final var currentDirectory = directories.removeFirst();
            this.readFromPath(directoryName, currentDirectory.getPath(), token).ifPresent(collection -> {
                collection.getFiles().forEach(currentDirectory::addFile);
                collection.getDirectories().forEach(innerDirectory -> {
                    if (!seen.contains(innerDirectory.getPath().toString())) {
                        directories.add(innerDirectory);
                        currentDirectory.addDirectory(innerDirectory);
                    }
                });
            });
        }
    }

    /**
     * Sleep for 2 seconds
     */
    protected void sleep() {
        try{
            logger.info("Sleeping for 2 seconds");
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
