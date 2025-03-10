package org.iorio.core.repository;

/**
 * An element in a repository.
 *
 * @param <P> the type of the path
 */
public interface RepositoryElement<P> {
    /**
     * Get the name of the element.
     *
     * @return the name of the element
     */
    String getName();
    /**
     * Get the path of the element.
     *
     * @return the path of the element
     */
    P getPath();
}
