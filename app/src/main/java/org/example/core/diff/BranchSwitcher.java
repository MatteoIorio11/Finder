package org.example.core.diff;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class BranchSwitcher {
    public static void switchBranch(final String repositoryPath, final String branchName) {
        try {
            // Step 1: Get the current branch
            final String currentBranch = executeCommand(new File(repositoryPath), "git rev-parse --abbrev-ref HEAD").trim();
            System.out.println("Current Branch: " + currentBranch);

            // Step 2: Switch branch if necessary
            if (!currentBranch.equals(branchName)) {
                System.out.println("Switching to branch: " + branchName);
                executeCommand(new File(repositoryPath), "git checkout " + branchName);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String executeCommand(File workingDir, String command) throws IOException, InterruptedException {
        final ProcessBuilder processBuilder = new ProcessBuilder();

        // Use appropriate shell depending on OS
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
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        return output.toString();
    }
}
