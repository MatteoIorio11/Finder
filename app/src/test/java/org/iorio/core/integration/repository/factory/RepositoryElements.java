package org.iorio.core.integration.repository.factory;

import java.util.List;

public abstract class RepositoryElements {
    public static final List<String> files = List.of(
            "FinderTest/file1",
            "FinderTest/file2",
            "FinderTest/.gitignore"
    );
    public static final List<String> directories = List.of(
            "FinderTest/dir1"
    );
    public static final List<String> filesInDirectory = List.of(
            "FinderTest/dir1/file3"
    );
    private RepositoryElements(){}
}
