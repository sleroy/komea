/**
 *
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
public class BugHistory
{
    
    
    private List<BugChange> bugChanges = Lists.newArrayList();


    private DateTime        when;


    private String          who;
    
    
    
    /**
     * Returns the value of the field bugChanges.
     *
     * @return the bugChanges
     */
    public List<BugChange> getBugChanges() {


        return bugChanges;
    }


    /**
     * Returns the value of the field when.
     *
     * @return the when
     */
    public DateTime getWhen() {


        return when;
    }
    
    
    /**
     * Returns the value of the field who.
     *
     * @return the who
     */
    public String getWho() {


        return who;
    }
    
    
    public void setBugChanges(final List<BugChange> _bugChanges) {
    
    
        bugChanges = _bugChanges;
    }
    
    
    /**
     * Sets the field when with the value of _when.
     *
     * @param _when
     *            the when to set
     */
    public void setWhen(final DateTime _when) {


        when = _when;
    }
    
    
    /**
     * Sets the field who with the value of _who.
     *
     * @param _who
     *            the who to set
     */
    public void setWho(final String _who) {


        who = _who;
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "BugHistory [\\n\\tbugChanges="
                + bugChanges + ", \\n\\twhen=" + when + ", \\n\\twho=" + who + "]";
    }
}
