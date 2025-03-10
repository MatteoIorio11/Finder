package org.iorio.gui;
import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.diff.CheckDifference;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;

public class GUI {
    public static void main(String[] args) {
        SecretConfigurator.readSecrets(".env");
        SwingUtilities.invokeLater(GUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("Git Merge Conflict Finder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 5, 5));

        final JLabel ownerLabel = new JLabel("Owner:");
        final JTextField ownerField = new JTextField();
        final JLabel repoLabel = new JLabel("Repository Name:");
        final JTextField repoField = new JTextField();
        final JLabel tokenLabel = new JLabel("Access Token:");
        final JTextField tokenField = new JTextField();
        final JLabel pathLabel = new JLabel("Local Repo Path:");
        final JTextField pathField = new JTextField();
        final JLabel branchALabel = new JLabel("Branch A (remote):");
        final JTextField branchAField = new JTextField();
        final JLabel branchBLabel = new JLabel("Branch B (local):");
        final JTextField branchBField = new JTextField();

        panel.add(ownerLabel);
        panel.add(ownerField);
        panel.add(repoLabel);
        panel.add(repoField);
        panel.add(tokenLabel);
        panel.add(tokenField);
        panel.add(pathLabel);
        panel.add(pathField);
        panel.add(branchALabel);
        panel.add(branchAField);
        panel.add(branchBLabel);
        panel.add(branchBField);

        final JButton submitButton = new JButton("Find Conflicting Files");
        final JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(resultArea);

        submitButton.addActionListener(e -> {
            // Retrieve user input
            final String owner = ownerField.getText();
            final String repo = repoField.getText();
            final String token = tokenField.getText();
            final String localRepoPath = pathField.getText();
            final String branchA = branchAField.getText();
            final String branchB = branchBField.getText();

            // Call your function to find conflicting files (placeholder for now)
            final String result = findConflictingFiles(owner, repo, token, localRepoPath, branchA, branchB);
            resultArea.setText(result);
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.NORTH);
        frame.add(submitButton, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static String findConflictingFiles(String owner, String repo, String token, String localRepoPath, String branchA, String branchB) {
        try {
            final var x = CheckDifference.checkDifference(owner, repo, token, localRepoPath, branchA, branchB);
            final StringBuilder output = new StringBuilder();
            for (final var entry : x) {
                output.append("""
                        Differences:
                        \t Local: %s
                        \t Remote: %s
                        """.formatted(entry.getKey().getPath(), entry.getValue().getPath()));
            }
            return output.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
