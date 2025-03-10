package org.iorio.core.integration.repository.file;

import org.apache.commons.lang3.SystemUtils;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.local.LocalFileReaderImpl;

import java.io.File;
import java.nio.file.Path;

public class LocalRepositoryFileIntegrationTest extends AbstractRepositoryFileIntegrationTest<Path, AbstractRepositoryFile<Path>> {
    public LocalRepositoryFileIntegrationTest() {
        super(
                new LocalFileImpl(
                        "FinderTest/file1",
                        Path.of(SystemUtils.getUserHome() + File.separator + "FinderTest" + File.separator + "file1")
                ),
                new LocalFileReaderImpl()
        );
    }
}
