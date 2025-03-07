package org.example.core.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class SecretConfigurator {
    public static void readSecrets(final String secretPath, final String fileName) {
        final Dotenv dotenv = Dotenv.configure().directory(secretPath).filename(fileName).load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}
