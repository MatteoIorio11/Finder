package org.iorio.core.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class SecretConfigurator {
    /**
     * Read secrets from a file and set them as system properties
     * @param fileName the name of the file containing the secrets
     */
    public static void readSecrets(final String fileName, final String directory) {
        final Dotenv dotenv = Dotenv.configure().directory(directory).filename(fileName).load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    public static void readTestSecrets() {
        readSecrets(".env", "");
    }

}
