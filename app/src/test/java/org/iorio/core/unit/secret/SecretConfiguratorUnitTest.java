package org.iorio.core.unit.secret;

import io.github.cdimascio.dotenv.DotenvException;
import jdk.jfr.Description;
import org.iorio.core.configuration.SecretConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class SecretConfiguratorUnitTest {
    @AfterAll
    public static void setProperties() {
        SecretConfigurator.readSecrets(".env");
    }
    @Description("If the file does not exist, the application should throw an exception")
    @Test
    @Tag("unit")
    public void testTryReadSecretsWithoutFile() {
        assertThrows(DotenvException.class,() -> SecretConfigurator.readSecrets(".notafile"));
    }

    @Description("If the file exists, then It should be possible to read the secrets")
    @Test
    @Tag("unit")
    public void testCheckIfSecretsAreLoaded() {
        SecretConfigurator.readSecrets( ".env");
        assertFalse(System.getProperties().isEmpty());
        assertNotNull(System.getProperty("GITHUB_TOKEN"));
    }
}
