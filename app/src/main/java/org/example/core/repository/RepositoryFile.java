package org.example.core.repository;

public interface RepositoryFile<P> extends RepositoryElement<P> {
    String getContent();
}
