package org.iorio.core.integration.checker;

import org.apache.commons.lang3.SystemUtils;
import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.diff.CheckDifference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
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
                        SystemUtils.getUserHome() + File.separator + "FinderTest",
                        "main",
                        "dev"
                        );
        final var fileNames1 =  diff.stream().map(entry -> entry.getKey().getName() + "-" + entry.getValue().getName()).collect(Collectors.joining());
        System.err.println();
        assertTrue(expected.contains(fileNames1));
    }
}
