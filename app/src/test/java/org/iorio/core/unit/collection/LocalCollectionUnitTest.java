package org.iorio.core.unit.collection;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;
import org.iorio.core.repository.local.LocalCollection;
import org.iorio.core.repository.local.LocalDirectoryImpl;
import org.iorio.core.repository.local.LocalFileImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocalCollectionUnitTest extends AbstractRepositoryCollectionUnitTest<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> {

    private static RepositoryCollection<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> collection;
    private static final List<AbstractRepositoryFile<Path>> files = List.of(
            new LocalFileImpl("FileTest", Path.of("src/test/resources/.env")),
            new LocalFileImpl("FileTest2", Path.of("app/src/main/resources/.env"))
    );
    private static final List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories = List.of(
            new LocalDirectoryImpl("main", Path.of("app/src/main")),
            new LocalDirectoryImpl("test", Path.of("app/src/test"))
    );
    protected LocalCollectionUnitTest() {
        super(collection);
    }

    @BeforeAll
    public static void init() {
        collection = new LocalCollection(files, directories);
    }

    @Description("If we get the list of files, then It should return the list of files")
    @Test
    @Tag("unit")
    public void testGetFiles() {
        assertFalse(collection.getFiles().isEmpty());
        assertEquals(files, collection.getFiles());
    }

    @Description("If we get the list of directories, then It should return the list of directories")
    @Test
    @Tag("unit")
    public void testGetDirectories() {
        assertFalse(collection.getDirectories().isEmpty());
        assertEquals(directories, collection.getDirectories());
    }

}
