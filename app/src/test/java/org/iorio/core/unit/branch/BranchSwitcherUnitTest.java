package org.iorio.core.unit.branch;

import jdk.jfr.Description;
import org.apache.commons.lang3.SystemUtils;
import org.iorio.core.diff.BranchSwitcher;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class BranchSwitcherUnitTest {

    @Description("If the repository path is null, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testSwitchBranchWithNullRepositoryPath() {
        assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch(null, "branch"));
        assertEquals("Repository path and branch name must not be null", assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch(null, "branch")).getMessage());
    }

    @Description("If the branch name is null, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testSwitchBranchWithNullBranchName() {
        assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch("path", null));
        assertEquals("Repository path and branch name must not be null", assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch("path", null)).getMessage());
    }

    @Description("If the input path does not correspond to an existing file, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testSwitchBranchWithNonExistentRepositoryPath() {
        assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch("path", "branch"));
        assertEquals("Repository path does not exist", assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch("path", "branch")).getMessage());
    }

    @Description("If the input path is a file and not a directory, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testSwitchBranchWithFileRepositoryPath() {
        final var path = Arrays.stream(Objects.requireNonNull(SystemUtils.getJavaIoTmpDir().listFiles()))
                .filter(File::isFile)
                        .findFirst();
        if (path.isEmpty()) {
            fail();
        }
        assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch(path.get().getPath(), "branch"));
        assertEquals("Repository path does not exist",
                assertThrows(IllegalArgumentException.class,
                () -> BranchSwitcher.switchBranch(path.get().getPath(), "branch")).getMessage());
    }

    @Description("If the path is valid, then It should be possible to switch branches")
    @Test
    @Tag("unit")
    public void testSwitchBranch() throws IOException, InterruptedException {
        final String expectedBranch = "dev";
        final String repoPath = SystemUtils.getUserHome() + File.separator + "FinderTest";
        final String command = "git rev-parse --abbrev-ref HEAD";
        BranchSwitcher.switchBranch(repoPath, expectedBranch);
        final String currentBranch = executeCommand(new File(repoPath), command).trim();
        assertEquals(expectedBranch, currentBranch);
    }

    private String executeCommand(File workingDir, String command) throws InterruptedException, IOException {
        final ProcessBuilder processBuilder = new ProcessBuilder();
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
