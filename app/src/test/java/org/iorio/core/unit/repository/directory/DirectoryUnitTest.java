package org.iorio.core.unit.repository.directory;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.local.LocalDirectoryImpl;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.unit.repository.AbstractRepositoryElementUnitTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DirectoryUnitTest extends AbstractRepositoryElementUnitTest<Path> {
    private static AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> directory;

    public DirectoryUnitTest() {
        super(directory);
    }

    @BeforeAll
    public static void init() {
        directory = new LocalDirectoryImpl("DirectoryTest", Path.of("src/test/resources"));
    }

    @Description("Adding a file into a directory It should be possible to retrieve it")
    @Test
    @Tag("unit")
    public void testExistingFile() {
        final var file = new LocalFileImpl("FileTest", Path.of("src/test/resources"));
        directory.addFile(file);
        assertTrue(directory.hasFile("FileTest"));
        assertTrue(directory.getFile("FileTest").isPresent());
        assertEquals(file, directory.getFile("FileTest").get());
    }

    @Description("Adding a directory into a directory It should be possible to retrieve it")
    @Test
    @Tag("unit")
    public void testExistingDirectory() {
        final var innerDirectory = new LocalDirectoryImpl("InnerDirectoryTest", Path.of("src/test/resources"));
        directory.addDirectory(innerDirectory);
        assertTrue(directory.hasDirectory("InnerDirectoryTest"));
        assertTrue(directory.getDirectory("InnerDirectoryTest").isPresent());
        assertEquals(innerDirectory, directory.getDirectory("InnerDirectoryTest").get());
    }

    @Description("Checking for a directory that does not exist should return false")
    @Test
    @Tag("unit")
    public void testNonExistingDirectory() {
        assertFalse(directory.hasDirectory("NonExistingDirectory"));
        assertTrue(directory.getDirectory("NonExistingDirectory").isEmpty());
    }

    @Description("Checking for a file that does not exist should return false")
    @Test
    @Tag("unit")
    public void testNonExistingFile() {
        assertFalse(directory.hasFile("NonExistingFile"));
        assertTrue(directory.getFile("NonExistingFile").isEmpty());
    }
}
