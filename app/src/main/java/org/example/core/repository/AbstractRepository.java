package org.example.core.repository;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * An abstract repository
 * @param <P> the type of the path
 * @param <X> the type of the directory
 * @param <Y> the type of the file
 */
public abstract class AbstractRepository<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>> implements RepositoryElement<P>{
    protected final static Logger logger = LoggerFactory.getLogger(AbstractRepository.class);
    private final Map<String, X> directories;
    private final Map<String, Y> files;
    private final String name;
    private final P path;

    /** Constructor for AbstractRepository
     * @param name the name of the repository
     * @param path the path of the repository
     */
    protected AbstractRepository(final String name, final P path) {
        this.name = name;
        this.path = path;
        this.directories = new HashMap<>();
        this.files = new HashMap<>();
        logger.info("Created repository: {}", this);
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
     * Add a directory to the repository
     * @param directory the directory to add
     */
    public void addDirectory(@NotNull final X directory) {
        logger.info("Adding directory: " + directory.getName());
        this.directories.put(directory.getName(), directory);
    }

    /**
     * Add a file to the repository
     * @param file the file to add
     */
    public void addFile(@NotNull final Y file){
        logger.info("Adding file: " + file.getName());
        this.files.put(file.getName(), file);
    }
    /**
     * Get the directories in the repository
     * @return the directories in the repository
     */
    public List<X> getDirectories(){
        return directories.values().stream().toList();
    }
    /**
     * Get the files in the repository
     * @return the files in the repository
     */
    public List<Y> getFiles(){
        return files.values().stream().toList();
    }

    /**
     * Check if the repository has a directory with the given name
     * @param name the name of the directory
     * @return true if the repository has a directory with the given name, false otherwise
     */
    public boolean hasDirectory(@NotNull final String name){
        logger.info("Checking if repository has directory: " + name);
        return this.directories.containsKey(name);
    }

    /**
     * Get the directory with the given name
     * @param name the name of the directory
     * @return the directory with the given name
     */
    public Optional<X> getDirectory(@NotNull final String name){
        return Optional.ofNullable(this.directories.get(name));
    }

    /**
     * Get the file with the given name
     * @param name the name of the file
     * @return the file with the given name
     */
    public Optional<Y> getFile(@NotNull final String name){
        return Optional.ofNullable(this.files.get(name));
    }

    /**
     * Check if the repository has a file with the given name
     * @param name the name of the file
     * @return true if the repository has a file with the given name, false otherwise
     */
    public boolean hasFile(final @NotNull String name){
        logger.info("Checking if repository has file: " + name);
        return this.files.containsKey(name);
    }

    @Override
    public String toString() {
        return "AbstractRepository{" +
                "directories=" + this.directories +
                ", files=" + this.files +
                ", name='" + this.name + '\'' +
                ", path=" + this.path +
                '}';
    }
}
