/**
 *
 */

package org.komea.product.backend.service.groovy;



import java.util.Collections;
import java.util.Map;

import org.komea.product.backend.service.plugins.IQueryAnnotations;



/**
 * @author sleroy
 */
public class QueryAnnotations implements IQueryAnnotations
{


    private final Map<String, Object> annotations;



    /**
     * @param _annotations
     */
    public QueryAnnotations(final Map<String, Object> _annotations) {


        annotations = Collections.unmodifiableMap(_annotations);


    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryAnnotations#existAnnotation(java.lang.String)
     */
    @Override
    public boolean existAnnotation(final String _annotation) {


        return annotations.containsKey(_annotation);
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryAnnotations#getAnnotation(java.lang.String)
     */
    @Override
    public <T> T getAnnotation(final String _annotationName) {


        return (T) annotations.get(_annotationName);
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.plugins.IQueryAnnotations#getAnnotationOrFail(java.lang.String)
     */
    @Override
    public <T> T getAnnotationOrFail(final String _string) {


        if (!existAnnotation(_string)) {
            throw new IllegalArgumentException("Missing annotation " + _string);
        }

        return (T) annotations.get(_string);
        
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryAnnotations#getAnnotations()
     */
    @Override
    public Map<String, Object> getAnnotations() {


        return annotations;
    }

}
