package org.example.core.remote;

import org.example.core.repository.RepositoryElement;

public interface RemoteElement extends RepositoryElement {
    String getRemoteUrl();
}
