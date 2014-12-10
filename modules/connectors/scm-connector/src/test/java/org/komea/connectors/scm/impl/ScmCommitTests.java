package org.komea.connectors.scm.impl;

import org.junit.Assert;
import org.junit.Test;


public class ScmCommitTests
{
    
    @Test
    public void test() {
    
        ScmCommit c = new ScmCommit();
        c.setNumberOfAddedLines(1);
        c.setNumberOfChangedLines(2);
        c.setNumberOfDeletedLines(3);
        
        Assert.assertEquals(3, c.getTotalNumberOfModifiedLines());
    }
    
}
