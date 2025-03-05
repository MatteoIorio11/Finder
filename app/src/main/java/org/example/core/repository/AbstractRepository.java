package org.example.core.repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRepository<P, X extends RepositoryDirectory<P, Y>, Y extends RepositoryFile<P>>{
    private final List<X> directories;
    private final List<Y> files;
    protected AbstractRepository() {
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
