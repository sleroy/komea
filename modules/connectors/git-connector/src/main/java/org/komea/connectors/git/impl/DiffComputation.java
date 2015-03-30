
package org.komea.connectors.git.impl;


import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.output.NullOutputStream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.komea.connectors.git.IFileUpdate;
import org.komea.connectors.git.IGitCommit;

/**
 * @author sleroy
 */
public class DiffComputation
{

    private final RevCommit  commit;
    private final Git        git;

    private final IGitCommit scmCommit;

    /**
     * @param scmCommit
     * @param commit
     * @param revWalk
     */
    public DiffComputation(final Git _git, final IGitCommit _convertGitCommit, final RevCommit _commit) {

        super();

        this.git = _git;

        this.scmCommit = _convertGitCommit;
        this.commit = _commit;

    }

    private IFileUpdate buildUpdate(final FileHeader header) {

        int nbAddedLines = 0;
        int nbDeletedLines = 0;
        final EditList edits = header.toEditList();
        for (final Edit edit : edits) {
            switch (edit.getType()) {
                case DELETE:
                    nbDeletedLines += edit.getLengthA();
                    break;
                case INSERT:
                    nbAddedLines += edit.getLengthB();
                    break;
                case REPLACE:
                    nbAddedLines += edit.getLengthB();
                    nbDeletedLines += edit.getLengthA();
                    break;
                default:

            }

        }
        final FileUpdate fileUpdate = new FileUpdate();
        fileUpdate.setPath(header.getNewPath());
        fileUpdate.setOldPath(header.getOldPath());
        fileUpdate.setNumberOfAddedLines(nbAddedLines);
        fileUpdate.setNumberOfDeletedLines(nbDeletedLines);
        return fileUpdate;

    }

    /**
     * Compute diff informations from the parent commit.
     *
     * @return
     * @throws GitAPIException
     * @throws IncorrectObjectTypeException
     * @throws IOException
     */
    public List<DiffEntry> diff() throws GitAPIException, IncorrectObjectTypeException, IOException {

        final ObjectReader reader = this.git.getRepository().newObjectReader();

        // prepare the two iterators to compute the diff between
        final AnyObjectId head = this.commit.getTree().getId();
        final CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        newTreeIter.reset(reader, head);

        AbstractTreeIterator oldTreeIter;
        if (hasCommitParent()) {

            RevCommit parent = this.commit.getParent(0);
            final AnyObjectId oldHead = parent.getTree().getId();
            oldTreeIter = new CanonicalTreeParser();
            ((CanonicalTreeParser) oldTreeIter).reset(reader, oldHead);

        } else {
            oldTreeIter = new EmptyTreeIterator();
        }
        // finally get the list of changed files
        final List<DiffEntry> diffs = this.git.diff().setNewTree(newTreeIter).setOldTree(oldTreeIter).call();
        return diffs;
    }

    private boolean hasCommitParent() {

        return this.commit.getParentCount() > 0;
    }

    private DiffFormatter initializeDiffFormater(final OutputStream out) {

        DiffFormatter df;
        df = new DiffFormatter(out);
        df.setRepository(this.git.getRepository());
        df.setDiffComparator(RawTextComparator.WS_IGNORE_TRAILING);
        df.setDetectRenames(true);
        return df;
    }

    /**
     * Update Komea commit informations using diff informations from the parent commit.
     *
     * @throws Exception
     */
    public void update() throws Exception {

        final DiffFormatter df = initializeDiffFormater(new NullOutputStream());

        try {

            final List<DiffEntry> diffs = diff();

            final Set<String> numberOfModifiedFileSet = new HashSet<String>();
            int added = 0, deleted = 0;
            for (final DiffEntry diff : diffs) {
                final FileHeader fileHeader = df.toFileHeader(diff);
                numberOfModifiedFileSet.add(fileHeader.getOldPath());
                final IFileUpdate update = buildUpdate(fileHeader);
                this.scmCommit.addFileUpdate(update);
                added += update.getNumberOfAddedLines();
                deleted += update.getNumberOfDeletedLines();

            }
            this.scmCommit.setNumberOfAddedLines(added);
            this.scmCommit.setNumberOfDeletedLines(deleted);
            this.scmCommit.setNumberOfModifiedFiles(numberOfModifiedFileSet.size());
        } finally {

            if (df != null) {
                df.release();
            }
        }

    }

}
