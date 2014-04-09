/**
 * 
 */

package org.komea.product.plugins.git.utils;



import org.eclipse.jgit.lib.Ref;
import org.springframework.core.convert.converter.Converter;



/**
 * This class converts a reference to a branch name;
 * 
 * @author sleroy
 */
public class GitRefToBranchNameConverter implements Converter<Ref, String>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public String convert(final Ref _source) {
    
    
        return _source.getName();
    }
    
}
