package org.komea.connectors.git;

import java.io.Closeable;
import org.komea.event.model.KomeaEvent;

public interface IGitEventsStorage extends Closeable {

    void pushCommit(IGitCommit convertGitCommit);

    void pushEvent(KomeaEvent event);

}
