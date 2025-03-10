package org.iorio.core.repository;

import java.util.List;

/**
 * A collection of files and directories
 * @param <P> the type of the path
 * @param <X> the type of the directory
 * @param <Y> the type of the file
 */
public interface RepositoryCollection<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>> {
    /**
     * Get the files in the collection
     * @return the files in the collection
     */
    List<Y> getFiles();
    /**
     * Get the directories in the collection
     * @return the directories in the collection
     */
    List<X> getDirectories();
}
