package org.komea.connectors.git.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.utils.DiffParser;

/**
 * @author sleroy
 */
public class DiffComputation {

	private final RevCommit commit;
	private final Git git;
	private final RevWalk revWalk;

	private final IGitCommit scmCommit;

	/**
	 * @param scmCommit
	 * @param commit
	 * @param revWalk
	 */
	public DiffComputation(final Git _git, final IGitCommit _convertGitCommit,
			final RevCommit _commit, final RevWalk _revWalk) {

		super();

		this.git = _git;

		this.scmCommit = _convertGitCommit;
		this.commit = _commit;
		this.revWalk = _revWalk;

	}

	public List<DiffEntry> obtainDiffEntriesFromCommit(final DiffFormatter df)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {
	    df.setRepository(this.git.getRepository());
	    
		List<DiffEntry> diffs;
		if (this.hasCommitParent()) {
			RevCommit parent = null;
			parent = this.revWalk.parseCommit(this.commit.getParent(0).getId());
			diffs = df.scan(parent.getTree(), this.commit.getTree());
		} else {
			diffs = df.scan(
					new EmptyTreeIterator(),
					new CanonicalTreeParser(null, this.revWalk
							.getObjectReader(), this.commit.getTree()));

		}
		return diffs;
	}

	/**
	 * @throws Exception
	 */
	public void updateCommitWithDiffInformations() throws Exception {

		ByteArrayOutputStream out = null;
		DiffFormatter df = null;
		try {
			out = new ByteArrayOutputStream();
			df = this.initializeDiffFormater(out);

			List<DiffEntry> diffs = this.obtainDiffEntriesFromCommit(df);

			final Set<String> numberOfModifiedFileSet = new HashSet<String>();
			for (final DiffEntry diff : diffs) {
				numberOfModifiedFileSet.add(diff.getOldPath());
				df.format(diff);
			}
			this.readDiffFileAndUpdatesScmCommit(this.splitDiffInLines(out));
			this.scmCommit.setNumberOfModifiedFiles(numberOfModifiedFileSet
					.size());
		} finally {
			if (out != null) {
				IOUtils.closeQuietly(out);
			}
			if (df != null) {
				df.release();
			}
		}

	}

	private boolean hasCommitParent() {

		return this.commit.getParentCount() > 0;
	}

	private DiffFormatter initializeDiffFormater(final ByteArrayOutputStream out) {

		DiffFormatter df;
		df = new DiffFormatter(out);
		df.setRepository(this.git.getRepository());
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);
		return df;
	}

	/**
	 * @param _out
	 * @throws Exception
	 */
	private void readDiffFileAndUpdatesScmCommit(final List<String> _diffLines)
			throws Exception {

		final DiffParser diffParser = new DiffParser(_diffLines);
		diffParser.computeStats();
		this.scmCommit.setNumberOfDeletedLines(diffParser.getDeletedLines());
		this.scmCommit.setNumberOfAddedLines(diffParser.getAddedLines());
		this.scmCommit.setNumberOfChangedLines(diffParser.getChangedLines());

	}

	private List<String> splitDiffInLines(final ByteArrayOutputStream out)
			throws UnsupportedEncodingException {

		return Arrays.asList(out.toString("UTF-8").split("\n"));
	}
}
