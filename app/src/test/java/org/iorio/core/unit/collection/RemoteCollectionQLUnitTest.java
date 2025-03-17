package org.iorio.core.unit.collection;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;
import org.iorio.core.repository.local.LocalDirectoryImpl;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.remote.graphql.RemoteCollectionQL;
import org.iorio.core.repository.remote.graphql.RemoteDirectoryQLImpl;
import org.iorio.core.repository.remote.graphql.RemoteFileQLImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class RemoteCollectionQLUnitTest extends AbstractRepositoryCollectionUnitTest<String, AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>, AbstractRepositoryFile<String>>{
    private static RepositoryCollection<String, AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>, AbstractRepositoryFile<String>> collection;
    private static final List<AbstractRepositoryFile<String>> files = List.of(
            new RemoteFileQLImpl("FileTest", "src/test/resources/.env"),
            new RemoteFileQLImpl("FileTest2", "app/src/main/resources/.env")
    );
    private static final List<AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>>> directories = List.of(
            new RemoteDirectoryQLImpl("main", "app/src/main"),
            new RemoteDirectoryQLImpl("test", "app/src/test")
    );

    protected RemoteCollectionQLUnitTest() {
        super(collection);
    }

    @BeforeAll
    public static void init() {
        collection = new RemoteCollectionQL(files, directories);
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
