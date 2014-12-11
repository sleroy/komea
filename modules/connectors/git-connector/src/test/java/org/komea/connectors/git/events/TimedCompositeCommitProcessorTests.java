
package org.komea.connectors.git.events;


import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;

public class TimedCompositeCommitProcessorTests extends AbstractLocalGitTest
{
    
    @Override
    public void init() {
    
        super.init();
        
    }
    @Test
    public void processWithNoTimeRestrictionsTest() {
    
        TimedCompositeCommitProcessor processor = new TimedCompositeCommitProcessor(new DateTime(2000, 01, 01, 0, 0), DateTime.now());
        
        CountingProcessor p1 = new CountingProcessor();
        CountingProcessor p2 = new CountingProcessor();
        processor.addCommitProcessor(p1);
        processor.addCommitProcessor(p2);
        
        this.repository.processAllCommits(processor);
        
        assertEquals(getExpectedNumberOfCommits(), p1.counter);
        assertEquals(getExpectedNumberOfCommits(), p2.counter);
    }
    
    @Test
    public void processWithSinceRestrictionsTest() {
    
        TimedCompositeCommitProcessor processor = new TimedCompositeCommitProcessor(new DateTime(2014, 01, 01, 0, 0), DateTime.now());
        
        CountingProcessor p1 = new CountingProcessor();
        CountingProcessor p2 = new CountingProcessor();
        
        processor.addCommitProcessor(p1);
        processor.addCommitProcessor(p2);
        
        this.repository.processAllCommits(processor);
        
        assertEquals(82, p1.counter);
        assertEquals(82, p2.counter);
    }
    
    @Test
    public void processWithUntilRestrictionsTest() {
    
        TimedCompositeCommitProcessor processor = new TimedCompositeCommitProcessor(new DateTime(2000, 01, 01, 0, 0),new DateTime(2014, 03, 01, 0, 0));
        
        CountingProcessor p1 = new CountingProcessor();
        CountingProcessor p2 = new CountingProcessor();
        
        processor.addCommitProcessor(p1);
        processor.addCommitProcessor(p2);
        
        this.repository.processAllCommits(processor);
        
        assertEquals(95, p1.counter);
        assertEquals(95, p2.counter);
    }
    
    private static class CountingProcessor implements IGitCommitProcessor
    {
        
        private int counter;
        
        @Override
        public void process(final RevCommit commit, final RevWalk revWalk, final IGitCommit convertGitCommit) {
        
            this.counter++;
            
        }
        
    }
}
