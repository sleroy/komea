/**
 * 
 */

package org.komea.product.plugins.scm;



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
    
    
        diffLines = _diffCommit;
        
        
    }
    
    
    /**
     * Computes stats about the diff.
     * 
     * @throws IOException
     */
    public void computeStats() throws IOException {
    
    
        final Patch<String> parseUnifiedDiff = DiffUtils.parseUnifiedDiff(diffLines);
        final List<Delta<String>> deltas = parseUnifiedDiff.getDeltas();
        for (final Delta delta : deltas) {
            
            
            switch (delta.getType()) {
                case CHANGE:
                    // changedLines += delta.getOriginal().size();
                    deletedLines += delta.getOriginal().size();
                    addedLines += delta.getRevised().size();
                    break;
                case DELETE:
                    deletedLines += delta.getOriginal().size();
                    break;
                case INSERT:
                    addedLines += delta.getOriginal().size();
                    break;
                default:
                    throw new UnsupportedOperationException();
                    
                    
            }
        }
        
        
    }
    
    
    public int getAddedLines() {
    
    
        return addedLines;
    }
    
    
    public int getChangedLines() {
    
    
        return changedLines;
    }
    
    
    public int getDeletedLines() {
    
    
        return deletedLines;
    }
    
    
    public void setChangedLines(final int _changedLines) {
    
    
        changedLines = _changedLines;
    }
    
}
