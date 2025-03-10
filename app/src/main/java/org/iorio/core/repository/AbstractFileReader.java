package org.iorio.core.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * An abstract file reader.
 *
 * @param <P> the type of the path
 */
public abstract class AbstractFileReader<P> {
    protected final static Logger logger = LoggerFactory.getLogger(AbstractFileReader.class);
    /**
     * Get the content of the file at the given path.
     *
     * @param path the path of the file
     * @return the content of the file
     */
    public abstract List<String> getContent(final P path);
}
