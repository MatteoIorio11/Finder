package org.example;

import org.example.core.configuration.SecretConfigurator;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class AbstractSecretConfigurator {
    @BeforeAll
    private void init() {
        SecretConfigurator.readSecrets("test/main/resources", ".env");
    }

    
}
