package org.example.core.repository;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRepository<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends RepositoryFile<P>>{
    private final List<X> directories;
    private final List<Y> files;
    private final String name;
    private final URL remoteUrl;

    protected AbstractRepository(final String name, final URL remoteUrl) {
        this.name = name;
        this.remoteUrl = remoteUrl;
        this.directories = new LinkedList<>();
        this.files = new LinkedList<>();
    }
    public void addDirectory(X directory) {
        this.directories.add(directory);
    }
    public void addFile(Y file){
        this.files.add(file);
    }
    public List<X> getDirectories(){
        return Collections.unmodifiableList(directories);
    }
    public List<Y> getFiles(){
        return Collections.unmodifiableList(files);
    }
}
