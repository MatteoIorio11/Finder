package org.example.core.unit;

import io.github.cdimascio.dotenv.DotenvException;
import jdk.jfr.Description;
import org.example.core.configuration.SecretConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class SecretConfiguratorUnitTest {
    @AfterAll
    public static void setProperties() {
        SecretConfigurator.readSecrets("src/test/resources", ".env");
    }
    @Description("If the file does not exist, the application should throw an exception")
    @Test
    @Tag("unit")
    public void testTryReadSecretsWithoutFile() {
        assertThrows(DotenvException.class,() -> SecretConfigurator.readSecrets("src/NOT_A_DIRECTORY", ".env"));
    }

    @Description("If the file exists, then It should be possible to read the secrets")
    @Test
    @Tag("unit")
    public void testCheckIfSecretsAreLoaded() {
        SecretConfigurator.readSecrets("src/test/resources", ".env");
        assertFalse(System.getProperties().isEmpty());
        assertNotNull(System.getProperty("TEST"));
    }
}
