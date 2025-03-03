package org.example.core.remote;

public class RemoteFileImpl implements RemoteFile{

    private final String name;
    public RemoteFileImpl(final String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getRemoteUrl() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String toString() {
        return "RemoteFileImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
