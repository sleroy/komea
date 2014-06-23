
package org.komea.product.backend.utils;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.Validate;



/**
 * This type describes the String list. A list of string is splitted when the character ";" or "," is encountered.
 * <p>
 *
 * @author $Author: sleroy $
 * @since 6 janv. 2011
 * @copyright Copyright (C) 2010 TOCEA
 */
public final class StringList implements Serializable
{
    
    
    public static final StringList EMPTY       = new StringList("");
    
    
    private final Set<String>      splitString = new HashSet<String>();
    
    
    
    public StringList() {
    
    
        super();
    }
    
    
    /**
     * Builds a String list from a string
     *
     * @param string
     *            the string
     */
    public StringList(final String string) {
    
    
        this(string == null ? "" : string, ","); //$NON-NLS-1$
    }
    
    
    /**
     * Builds a String list from a string
     *
     * @param _str
     *            the string
     * @param _delimiters
     *            the reg exp
     */
    public StringList(final String _str, final String _delimiters) {
    
    
        fill(_str, _delimiters);
        
        
    }
    
    
    /**
     * @param _status
     * @return
     */
    public boolean contains(final String _status) {
    
    
        return splitString.contains(_status);
    }
    
    
    /**
     * This method returns the string list.
     *
     * @return the string list.
     */
    public List<String> getStringList() {
    
    
        return Collections.unmodifiableList(new ArrayList<String>(splitString));
    }
    
    
    /**
     * This method returns the string list.
     *
     * @return the string list.
     */
    public Set<String> getStringSet() {
    
    
        return Collections.unmodifiableSet(splitString);
    }
    
    
    /**
     * @return
     */
    public boolean isEmpty() {
    
    
        return splitString.isEmpty();
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "StringList [\\n\\tsplitString=" + splitString + "]";
    }
    
    
    private void fill(final String _str, final String _delimiters) {
    
    
        Validate.notNull(_str, _delimiters);
        
        final StringTokenizer tokenizer = new StringTokenizer(_str, _delimiters);
        
        while (tokenizer.hasMoreTokens()) {
            splitString.add(tokenizer.nextToken().trim());
        }
    }
}
