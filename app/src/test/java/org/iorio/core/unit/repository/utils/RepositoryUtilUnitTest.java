package org.iorio.core.unit.repository.utils;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryUtils;
import org.iorio.core.repository.local.LocalRepositoryImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RepositoryUtilUnitTest {
    @Description("Giving two repositories with common files, It should be possible to retrieve all the common files")
    @Test
    @Tag("unit")
    public void testCommonFilesFromRepository() {
    }
}
