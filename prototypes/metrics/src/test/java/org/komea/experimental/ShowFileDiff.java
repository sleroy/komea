
package org.komea.experimental;


/*
 * Copyright 2013, 2014 Dominik Stadler
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.komea.connectors.git.IFileUpdate;
import org.komea.connectors.git.impl.FileUpdate;
import org.komea.connectors.git.impl.GitRepository;

/**
 * Simple snippet which shows how to show diffs between branches
 * 
 * @author dominik.stadler at gmx.at
 */
public class ShowFileDiff
{
    
    public static void main(final String[] args) throws IOException, GitAPIException {
    
        GitRepository repository = new GitRepository(new File("/Users/afloch/Documents/git/mongo"), "");
        
        // the diff works on TreeIterators, we prepare two for the two branches
        AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, "451dccb3ec7801b0929a870f30adaf2f78286922");
        AbstractTreeIterator newTreeParser = prepareTreeParser(repository, "76427cd8a7f21210e0b41b0382ce05e05f092245");
        
        // then the procelain diff-command returns a list of diff entries
        List<DiffEntry> diff = new Git(repository.getGit().getRepository()).diff().setOldTree(oldTreeParser).setNewTree(newTreeParser)
                .setPathFilter(PathFilter.create("src/mongo/db/pipeline/expression.cpp")).call();
        for (DiffEntry entry : diff) {
            
            System.out.println("Entry: " + entry + ", from: " + entry.getOldId() + ", to: " + entry.getNewId());
            DiffFormatter formatter = new DiffFormatter(System.out);
           
            formatter.setDiffComparator(RawTextComparator.WS_IGNORE_TRAILING);
            formatter.setRepository(repository.getGit().getRepository());
            formatter.format(entry);
            
            final FileHeader fileHeader = formatter.toFileHeader(entry);
            IFileUpdate update = buildUpdate(fileHeader);
            System.out.println(update);
        }
        
        repository.close();
    }
    
    private static IFileUpdate buildUpdate(final FileHeader header) {

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
    private static AbstractTreeIterator prepareTreeParser(final GitRepository repository, final String objectId) throws IOException,
            MissingObjectException, IncorrectObjectTypeException {
    
        // from the commit we can build the tree which allows us to construct the TreeParser
        RevWalk walk = new RevWalk(repository.getGit().getRepository());
        RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
        RevTree tree = walk.parseTree(commit.getTree().getId());
        
        CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
        ObjectReader oldReader = repository.getGit().getRepository().newObjectReader();
        try {
            oldTreeParser.reset(oldReader, tree.getId());
        } finally {
            oldReader.release();
        }
        
        walk.dispose();
        
        return oldTreeParser;
    }
}
