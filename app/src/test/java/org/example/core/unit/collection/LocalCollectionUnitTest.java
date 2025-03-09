package org.example.core.unit.collection;

import org.checkerframework.checker.units.qual.A;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.RepositoryCollection;
import org.example.core.repository.local.LocalCollection;
import org.example.core.repository.local.LocalDirectoryImpl;
import org.example.core.repository.local.LocalFileImpl;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class LocalCollectionUnitTest extends AbstractRepositoryCollectionUnitTest<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> {

    private static RepositoryCollection<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> collection;
    protected LocalCollectionUnitTest() {
        super(collection);
    }

    @BeforeAll
    public static void init() {
        final List<AbstractRepositoryFile<Path>> files = List.of(
                new LocalFileImpl("FileTest", Path.of("src/test/resources/.env")),
                new LocalFileImpl("FileTest2", Path.of("app/src/main/resources/.env"))
        );
        final List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories = List.of(
            new LocalDirectoryImpl("main", Path.of("app/src/main")),
            new LocalDirectoryImpl("test", Path.of("app/src/test"))
        );


        collection = new LocalCollection(files, directories);
    }

}
