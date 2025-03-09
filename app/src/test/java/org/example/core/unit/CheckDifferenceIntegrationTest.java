package org.example.core.unit;

import org.apache.commons.lang3.SystemUtils;
import org.example.core.configuration.SecretConfigurator;
import org.example.core.diff.CheckDifference;
import org.example.core.repository.local.LocalFileImpl;
import org.example.core.repository.remote.RemoteFileImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckDifferenceIntegrationTest {

    @BeforeAll
    public static void init() {
        SecretConfigurator.readTestSecrets();
    }

    @Test
    public void testCheckDifference() throws MalformedURLException {
        final var expected = List.of(
                "FinderTest/dir1/file3" + "-" + "FinderTest/dir1/file3"
        );
        final var diff = CheckDifference
                .checkDifference("MatteoIorio11",
                        "FinderTest",
                        System.getProperty("GITHUB_TOKEN"),
                        SystemUtils.getUserHome() + "/" + "FinderTest",
                        "main",
                        "dev"
                        );
        final var fileNames1 =  diff.stream().map(entry -> entry.getKey().getName() + "-" + entry.getValue().getName()).collect(Collectors.joining());
        System.err.println();
        assertTrue(expected.contains(fileNames1));
    }
}
