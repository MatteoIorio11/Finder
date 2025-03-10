package org.iorio.core.repository;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * An abstract class for a file in a repository
 * @param <P> the type of the path
 */
public abstract class AbstractRepositoryFile<P> implements RepositoryElement<P> {
    protected final static Logger logger = LoggerFactory.getLogger(AbstractRepositoryFile.class);
    private final String name;
    private final P path;

    /**
     * Constructor for AbstractRepositoryFile
     * @param name the name of the file
     * @param path the path of the file
     */
    public AbstractRepositoryFile(final String name, final P path) {
        this.name = Objects.requireNonNull(name);
        this.path = Objects.requireNonNull(path);
        logger.info("Created file: {}", this.name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public P getPath() {
        return this.path;
    }

    /**
     * Get the content of the file lazily
     * @param reader the reader to use
     * @return the content of the file
     */
    public List<String> getContent(@NonNull final AbstractFileReader<P> reader) {
        logger.info("Getting content of file: " + this.getName());
        return reader.getContent(this.getPath());
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + this.getName() + '\'' +
                ", url='" + this.getPath() + '\'' +
                '}';
    }
}
