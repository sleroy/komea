/**
 *
 */

package org.komea.product.database.dto;



import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;



/**
 * @author sleroy
 */
public class BugHistory implements Serializable
{


    String added;

    int    bug_id;
    Date   bug_when;

    String field;
    
    
    String removed;


    int    who;
    
    
    
    /**
     * Returns the value of the field added.
     *
     * @return the added
     */
    public String getAdded() {


        return added;
    }


    /**
     * Returns the value of the field bug_id.
     *
     * @return the bug_id
     */
    public int getBug_id() {


        return bug_id;
    }


    /**
     * Returns the value of the field bug_when.
     *
     * @return the bug_when
     */
    public Date getBug_when() {


        return bug_when;
    }


    /**
     * Returns the value of the field field.
     *
     * @return the field
     */
    public String getField() {


        return field;
    }


    /**
     * Returns the value of the field removed.
     *
     * @return the removed
     */
    public String getRemoved() {


        return removed;
    }


    public DateTime getWhen() {
    
    
        return new DateTime(bug_when);
    }


    /**
     * Returns the value of the field who.
     *
     * @return the who
     */
    public int getWho() {


        return who;
    }


    /**
     * Sets the field added with the value of _added.
     *
     * @param _added
     *            the added to set
     */
    public void setAdded(final String _added) {


        added = _added;
    }


    /**
     * Sets the field bug_id with the value of _bug_id.
     *
     * @param _bug_id
     *            the bug_id to set
     */
    public void setBug_id(final int _bug_id) {


        bug_id = _bug_id;
    }


    /**
     * Sets the field bug_when with the value of _bug_when.
     *
     * @param _bug_when
     *            the bug_when to set
     */
    public void setBug_when(final Date _bug_when) {


        bug_when = _bug_when;
    }
    
    
    /**
     * Sets the field field with the value of _field.
     *
     * @param _field
     *            the field to set
     */
    public void setField(final String _field) {


        field = _field;
    }
    
    
    /**
     * Sets the field removed with the value of _removed.
     *
     * @param _removed
     *            the removed to set
     */
    public void setRemoved(final String _removed) {


        removed = _removed;
    }


    /**
     * Sets the field who with the value of _who.
     *
     * @param _who
     *            the who to set
     */
    public void setWho(final int _who) {


        who = _who;
    }


}
