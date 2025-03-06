package org.example.core.repository;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * An abstract class for a directory in a repository
 * @param <P> the type of the path
 * @param <Y> the type of the file
 */
public abstract class AbstractRepositoryDirectory<P, Y extends AbstractRepositoryFile<P>> implements RepositoryElement<P> {
    protected final static Logger logger = LoggerFactory.getLogger(AbstractRepositoryDirectory.class);
    private final Map<String, AbstractRepositoryDirectory<P, Y>> directories;
    private final Map<String, Y> files;
    private final String name;
    private final P remoteUrl;

    /**
     * Constructor for AbstractRepositoryDirectory
     * @param name the name of the directory
     * @param remoteUrl the remote url of the directory
     */
    public AbstractRepositoryDirectory(final String name, final P remoteUrl) {
        this.directories = new HashMap<>();
        this.files = new HashMap<>();
        this.name = Objects.requireNonNull(name);
        this.remoteUrl = Objects.requireNonNull(remoteUrl);
        logger.info("Created directory: " + this.getName());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public P getPath() {
        return this.remoteUrl;
    }

    /**
     * Get the files in the directory
     * @return the files in the directory
     */
    public List<Y> getFiles() {
        return this.files.values().stream().toList();
    }
    /**
     * Get the directories in the directory
     * @return the directories in the directory
     */
    public List<AbstractRepositoryDirectory<P, Y>> getInnerDirectories(){
        return this.directories.values().stream().toList();
    }
    /**
     * Add a file to the directory
     * @param file the file to add
     */
    public void addFile(@NotNull final Y file) {
        logger.info("Adding file: " + file.getName());
        this.files.put(file.getName(), file);
    }
    /**
     * Add a directory to the directory
     * @param directory the directory to add
     */
    public void addDirectory(@NotNull final AbstractRepositoryDirectory<P, Y> directory) {
        logger.info("Adding directory: " + directory.getName());
        this.directories.put(directory.getName(), directory);
    }

    /**
     * Check if the directory has a file
     * @param name the name of the file
     * @return true if the directory has the file, false otherwise
     */
    public boolean hasFile(@NotNull final String name) {
        logger.info("Checking if directory has file: " + name);
        return this.files.containsKey(name);
    }
    /**
     * Check if the directory has a directory
     * @param name the name of the directory
     * @return true if the directory has the directory, false otherwise
     */
    public boolean hasDirectory(@NotNull final String name) {
        logger.info("Checking if directory has directory: " + name);
        return this.directories.containsKey(name);
    }

    /**
     * Get the file with the given name
     * @param name the name of the file
     * @return the file with the given name
     */
    public Optional<Y> getFile(@NotNull final String name) {
        return Optional.ofNullable(this.files.get(name));
    }

    /**
     * Get the directory with the given name
     * @param name the name of the directory
     * @return the directory with the given name
     */
    public Optional<AbstractRepositoryDirectory<P, Y>> getDirectory(@NotNull final String name) {
        return Optional.ofNullable(this.directories.get(name));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractRepositoryDirectory && this.name.equals(((AbstractRepositoryDirectory<?, ?>) obj).name);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "name='" + name + '\'' +
                ", remoteUrl='" + remoteUrl + '\'' +
                '}';
    }
}
