package org.example.core.diff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A utility class to switch branches in a Git repository.
 */
public class BranchSwitcher {
    private final static Logger logger = LoggerFactory.getLogger(BranchSwitcher.class);
    /**
     * Switches the branch of a Git repository.
     *
     * @param repositoryPath the path to the Git repository
     * @param branchName     the name of the branch to switch to
     */
    public static void switchBranch(final String repositoryPath, final String branchName) {
        try {
            logger.info("Switching branch to: " + branchName);
            // Step 1: Get the current branch
            final String currentBranch = executeCommand(new File(repositoryPath), "git rev-parse --abbrev-ref HEAD").trim();
            logger.info("Current branch: " + currentBranch);
            // Step 2: Switch branch if necessary
            if (!currentBranch.equals(branchName)) {
                logger.info("Switching to branch: " + branchName);
                executeCommand(new File(repositoryPath), "git checkout " + branchName);
            }

        } catch (IOException | InterruptedException e) {
            logger.error("Failed to switch branch" + e.getMessage());
        }
    }

    private static String executeCommand(File workingDir, String command) throws IOException, InterruptedException {
        final ProcessBuilder processBuilder = new ProcessBuilder();

        // Use appropriate shell depending on OS
        logger.info("Executing command: " + command);
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            processBuilder.command("cmd.exe", "/c", command);
        } else {
            processBuilder.command("bash", "-c", command);
        }

        processBuilder.directory(workingDir);

        final Process process = processBuilder.start();
        process.waitFor();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final StringBuilder output = new StringBuilder();
        String line;
        logger.info("Output:");
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        logger.info(output.toString());
        return output.toString();
    }
}
