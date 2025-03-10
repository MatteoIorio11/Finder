package org.iorio.core.unit.collection;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractRepositoryCollectionUnitTest<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>> {
    private final RepositoryCollection<P, X, Y> repositoryCollection;

    protected AbstractRepositoryCollectionUnitTest(final RepositoryCollection<P, X, Y> repositoryCollection) {
        this.repositoryCollection = repositoryCollection;
    }

    @Description("If the user tries to modify the list of files, it should throw an exception.")
    @Test
    @Tag("unit")
    void testUnSupportedOperationOnFileList() {
        assertThrows(UnsupportedOperationException.class, () -> repositoryCollection.getFiles().add(null));
    }

    @Description("If the user tries to modify the list of directories, it should throw an exception.")
    @Test
    @Tag("unit")
    void testUnSupportedOperationOnDirectoryList() {
        assertThrows(UnsupportedOperationException.class, () -> repositoryCollection.getDirectories().add(null));
    }
}
