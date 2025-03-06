package org.example.core.repository;

public abstract class AbstractFileReader<P> {
    public abstract String getContent(final P path);
}
