package org.iorio.core.integration.repository.directory;

import org.apache.commons.lang3.SystemUtils;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.local.LocalDirectoryImpl;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.local.LocalFileReaderImpl;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.nio.file.Path;

public class LocalDirectoryIntegrationTest extends AbstractRepositoryDirectoryIntegrationTest<Path, AbstractRepositoryFile<Path>> {
    private static AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDirectory;
    public LocalDirectoryIntegrationTest() {
        super(
                localDirectory,
                new LocalFileReaderImpl()
        );
    }

    @BeforeAll
    public static void setUp() {
        localDirectory = new LocalDirectoryImpl(
                "FinderTest/dir1",
                Path.of(SystemUtils.getUserHome() + File.separator + "FinderTest" + File.separator + "dir1")
        );
        localDirectory.addFile(new LocalFileImpl(
                "FinderTest/dir1/file3",
                Path.of(SystemUtils.getUserHome() + File.separator + "FinderTest" + File.separator + "dir1" + File.separator + "file3")
        ));
    }
}
