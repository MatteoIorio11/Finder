package org.iorio.core.unit.repository.reader;

import jdk.jfr.Description;
import org.iorio.core.repository.FileReader;
import org.iorio.core.repository.remote.graphql.RemoteFileReaderQLImpl;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RemoteFileReaderQLUnitTest {
    private final FileReader<String> reader = new RemoteFileReaderQLImpl("MatteoIorio11",
            "FinderTest",
            "main");

    @Description("If the path is valid, then It should be possible to read the Its content")
    @Test
    @Tag("unit")
    public void testContentOfExistingFileShouldExist() {
        final List<String> content = reader.getContent("file1");
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }
}
