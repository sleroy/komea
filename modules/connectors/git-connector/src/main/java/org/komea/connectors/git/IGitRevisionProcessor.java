package org.komea.connectors.git;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public interface IGitRevisionProcessor {

	void process(RevCommit commit, RevWalk revWalk) throws Exception;

}
