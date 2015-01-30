package org.komea.connectors.git;

import java.io.Closeable;
import org.komea.events.dto.KomeaEvent;

public interface IGitEventsStorage extends Closeable {

    void pushCommit(IGitCommit convertGitCommit);

    void pushEvent(KomeaEvent event);

}
