package org.example.core.repository;

import kotlin.NotImplementedError;

import java.util.Optional;

public abstract class AbstractFileReader<P> {
    public abstract String getContent(final P path);
}
