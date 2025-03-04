package org.example.core.remote;

import java.util.List;

public record RemoteCollection(List<RemoteFile> files, List<RemoteDirectory<RemoteFile>> directories) {
}
