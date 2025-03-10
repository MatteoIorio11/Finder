package org.iorio.core.integration.repository.file;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractFileReader;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractRepositoryFileIntegrationTest<P, Y extends AbstractRepositoryFile<P>> {
    protected final AbstractRepositoryFile<P> repositoryFile;
    protected final AbstractFileReader<P> fileReader;
    protected AbstractRepositoryFileIntegrationTest(final AbstractRepositoryFile<P> repositoryFile, final AbstractFileReader<P> fileReader) {
        this.repositoryFile = repositoryFile;
        this.fileReader = fileReader;
    }

    @Description("If the repository file is valid, then it should be possible to read the content")
    @Test
    @Tag("integration")
    public void testReadContent() {
        final var content = repositoryFile.getContent(this.fileReader);
        assertNotNull(content);
        assertFalse(content.isEmpty());
        assertTrue(content.stream().anyMatch(line -> line.contains("Test")));
    }
}
