package org.example.core.unit;

import io.github.cdimascio.dotenv.DotenvException;
import jdk.jfr.Description;
import org.example.core.configuration.SecretConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

public class AbstractSecretConfigurator {
    @BeforeAll
    public static void init() {
        SecretConfigurator.readSecrets("src/test/resources", ".env");
    }

    @Description("If the file does not exist, the application should throw an exception")
    @Test
    @Tag("unit")
    public void testTryReadSecretsWithoutFile() {
        assertThrows(DotenvException.class,() -> SecretConfigurator.readSecrets("src/NOT_A_DIRECTORY", ".env"));
    }
}
