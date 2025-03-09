package org.example.core.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class SecretConfigurator {
    public static void readSecrets(final String fileName) {
        final Dotenv dotenv = Dotenv.configure().filename(fileName).load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    public static void readTestSecrets() {
        readSecrets(".env");
    }

}
