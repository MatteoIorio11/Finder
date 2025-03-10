package org.iorio.core.unit.branch;

import jdk.jfr.Description;
import org.apache.commons.lang3.SystemUtils;
import org.iorio.core.diff.BranchSwitcher;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
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
}
