package org.iorio.core.unit.repository.collection;

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

    @Description("If the user creates a local collection with a null list of files, this should throw an exception.")
    @Test
    @Tag("unit")
    public void testLocalCollectionNullFiles() {
        assertThrows(NullPointerException.class, () -> new LocalCollection(null, List.of()));
    }

    @Description("If the user creates a local collection with a null list of directories, this should throw an exception.")
    @Test
    @Tag("unit")
    public void testLocalCollectionNullDirectories() {
        assertThrows(NullPointerException.class, () -> new LocalCollection(List.of(), null));
    }

    @Description("If the user creates a local collection with non null values, It should be possible to retrieve those values")
    @Test
    @Tag("unit")
    public void testLocalCollection() {
        final List<AbstractRepositoryFile<Path>> files = List.of(
                new LocalFileImpl("file", Path.of("file1"))
        );
        final List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories = List.of(
                new LocalDirectoryImpl("directory", Path.of("directory1"))
        );
        final LocalCollection localCollection = new LocalCollection(files, directories);
        assertEquals(files, localCollection.getFiles());
        assertEquals(directories, localCollection.getDirectories());
    }

    @Description("It should not be possible to modify the list of files after creating a local collection")
    @Test
    @Tag("unit")
    public void testLocalCollectionImmutableFiles() {
        final List<AbstractRepositoryFile<Path>> files = List.of(
                new LocalFileImpl("file", Path.of("file1"))
        );
        final List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories = List.of(
                new LocalDirectoryImpl("directory", Path.of("directory1"))
        );
        final LocalCollection localCollection = new LocalCollection(files, directories);
        assertThrows(UnsupportedOperationException.class, () -> localCollection.getFiles().add(new LocalFileImpl("file", Path.of("file2"))));
        assertThrows(UnsupportedOperationException.class, () -> localCollection.getDirectories().add(new LocalDirectoryImpl("dir1", Path.of("dir1"))));
    }

}
