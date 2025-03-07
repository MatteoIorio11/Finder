package org.example.core.unit;

import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.RepositoryElement;
import org.example.core.repository.local.LocalDirectoryImpl;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Path;

public class DirectoryUnitTest extends AbstractRepositoryElementUnitTest<Path> {
    private static AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> directory;

    public DirectoryUnitTest() {
        super(directory);
    }

    @BeforeAll
    public static void init() {
        directory = new LocalDirectoryImpl("DirectoryTest", Path.of("src/test/resources"));
    }
}
