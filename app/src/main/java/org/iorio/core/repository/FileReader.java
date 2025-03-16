package org.iorio.core.repository;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * An abstract file reader.
 *
 * @param <P> the type of the path
 */
public interface FileReader<P> {
    Logger logger = LoggerFactory.getLogger(FileReader.class);
    /**
     * Get the content of the file at the given path.
     *
     * @param path the path of the file
     * @return the content of the file
     */
    List<String> getContent(@NonNull final P path);
}
