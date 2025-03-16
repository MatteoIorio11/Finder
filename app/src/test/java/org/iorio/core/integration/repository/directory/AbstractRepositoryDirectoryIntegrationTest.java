package org.iorio.core.integration.repository.directory;

import jdk.jfr.Description;
import org.iorio.core.repository.FileReader;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractRepositoryDirectoryIntegrationTest<P, Y extends AbstractRepositoryFile<P>> {
    private final AbstractRepositoryDirectory<P, Y> directory;
    private final FileReader<P> reader;
    protected AbstractRepositoryDirectoryIntegrationTest(final AbstractRepositoryDirectory<P, Y> directory,
                                                         final FileReader<P> reader) {
        this.directory = directory;
        this.reader = reader;
    }

    @Description("If the directory is valid, then It should be possible to read the content of the files")
    @Test
    @Tag("integration")
    public void testReadContent() {
        final var content = this.directory.getFile("FinderTest/dir1/file3");
        assertNotNull(content);
        assertFalse(content.isEmpty());
        assertTrue(content.get().getContent(this.reader).contains("Test"));
    }
}
