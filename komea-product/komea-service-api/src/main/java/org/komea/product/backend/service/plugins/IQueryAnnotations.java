/**
 *
 */

package org.komea.product.backend.service.plugins;



import java.util.Map;



/**
 * @author sleroy
 */
public interface IQueryAnnotations
{
    
    
    boolean existAnnotation(String _annotation);
    
    
    <T> T getAnnotation(String _annotationName);
    
    
    /**
     * Returns the annotation or throws an exception if missing.
     *
     * @param _string
     * @return
     */
    <T> T getAnnotationOrFail(String _string);
    
    
    Map<String, Object> getAnnotations();
}
