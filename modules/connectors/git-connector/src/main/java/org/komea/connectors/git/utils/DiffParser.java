/**
 * 
 */

package org.komea.connectors.git.utils;



import java.io.IOException;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;




/**
 * @author sleroy
 */
public class DiffParser
{
    
    
    private int                addedLines   = 0;
    private int                changedLines = 0;
    private int                deletedLines = 0;
    private final List<String> diffLines;
    
    
    
    /**
     * DIff parser
     * 
     * @param _bufferedReader
     */
    public DiffParser(final List<String> _diffCommit) {
    
    
        this.diffLines = _diffCommit;
        
        
    }
    
    
    /**
     * Computes stats about the diff.
     * 
     * @throws IOException
     */
    public void computeStats() throws IOException {
    
    
        final Patch<String> parseUnifiedDiff = DiffUtils.parseUnifiedDiff(this.diffLines);
        final List<Delta<String>> deltas = parseUnifiedDiff.getDeltas();
        for (final Delta delta : deltas) {
            
            
            switch (delta.getType()) {
                case CHANGE:
                    // changedLines += delta.getOriginal().size();
                    this.deletedLines += delta.getOriginal().size();
                    this.addedLines += delta.getRevised().size();
                    break;
                case DELETE:
                    this.deletedLines += delta.getOriginal().size();
                    break;
                case INSERT:
                    this.addedLines += delta.getOriginal().size();
                    break;
                default:
                    throw new UnsupportedOperationException();
                    
                    
            }
        }
        
        
    }
    
    
    public int getAddedLines() {
    
    
        return this.addedLines;
    }
    
    
    public int getChangedLines() {
    
    
        return this.changedLines;
    }
    
    
    public int getDeletedLines() {
    
    
        return this.deletedLines;
    }
    
    
    public void setChangedLines(final int _changedLines) {
    
    
        this.changedLines = _changedLines;
    }
    
}
