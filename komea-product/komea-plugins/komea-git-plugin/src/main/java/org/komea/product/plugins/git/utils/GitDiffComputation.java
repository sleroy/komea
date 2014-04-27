/**
 * 
 */

package org.komea.product.plugins.git.utils;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
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
import org.komea.product.plugins.scm.DiffParser;
import org.komea.product.plugins.scm.api.plugin.ScmCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class GitDiffComputation
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GitDiffComputation.class);
    private final RevCommit     commit;
    private final Git           git;
    private final RevWalk       revWalk;
    
    
    private final ScmCommit     scmCommit;
    
    
    
    /**
     * @param scmCommit
     * @param commit
     * @param revWalk
     */
    public GitDiffComputation(
            final Git _git,
            final ScmCommit _convertGitCommit,
            final RevCommit _commit,
            final RevWalk _revWalk) {
    
    
        super();
        
        git = _git;
        
        scmCommit = _convertGitCommit;
        commit = _commit;
        revWalk = _revWalk;
        
    }
    
    
    /**
     * @throws Exception
     */
    public void updateCommitWithDiffInformations() throws Exception {
    
    
        ByteArrayOutputStream out = null;
        DiffFormatter df = null;
        try {
            out = new ByteArrayOutputStream();
            df = initializeDiffFormater(out);
            
            
            List<DiffEntry> diffs = Collections.EMPTY_LIST;
            diffs = obtainDiffEntriesFromCommit(df);
            
            final Set<String> numberOfModifiedFileSet = new HashSet<String>();
            for (final DiffEntry diff : diffs) {
                numberOfModifiedFileSet.add(diff.getOldPath());
                
                df.format(diff);
                
                readDiffFileAndUpdatesScmCommit(splitDiffInLines(out));
                
                out.reset();
            }
            scmCommit.setNumberOfModifiedFiles(numberOfModifiedFileSet.size());
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
    
    
        return commit.getParentCount() > 0;
    }
    
    
    private DiffFormatter initializeDiffFormater(final ByteArrayOutputStream out) {
    
    
        DiffFormatter df;
        df = new DiffFormatter(out);
        df.setRepository(git.getRepository());
        df.setDiffComparator(RawTextComparator.DEFAULT);
        df.setDetectRenames(true);
        return df;
    }
    
    
    private List<DiffEntry> obtainDiffEntriesFromCommit(final DiffFormatter df)
            throws MissingObjectException, IncorrectObjectTypeException, IOException {
    
    
        List<DiffEntry> diffs;
        if (hasCommitParent()) {
            RevCommit parent = null;
            parent = revWalk.parseCommit(commit.getParent(0).getId());
            diffs = df.scan(parent.getTree(), commit.getTree());
        } else {
            diffs =
                    df.scan(new EmptyTreeIterator(),
                            new CanonicalTreeParser(null, revWalk.getObjectReader(), commit
                                    .getTree()));
            
        }
        return diffs;
    }
    
    
    /**
     * @param _out
     * @throws Exception
     */
    private void readDiffFileAndUpdatesScmCommit(final List<String> _diffLines) throws Exception {
    
    
        final DiffParser diffParser = new DiffParser(_diffLines);
        diffParser.computeStats();
        scmCommit.setNumberofDeletedLines(diffParser.getDeletedLines());
        scmCommit.setNumberOfAddedlines(diffParser.getAddedLines());
        scmCommit.setNumberOfChangedLines(diffParser.getChangedLines());
        
        
    }
    
    
    private List<String> splitDiffInLines(final ByteArrayOutputStream out)
            throws UnsupportedEncodingException {
    
    
        return Arrays.asList(out.toString("UTF-8").split("\n"));
    }
}
