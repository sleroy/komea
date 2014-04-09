/**
 * 
 */
package org.komea.product.plugins.scm;

import org.komea.product.backend.utils.SearchFilter;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;

/**
 * @author sleroy
 *
 */
final class ScmKeySearchFilter implements SearchFilter<ScmRepositoryDefinition>
{
    
    
    /**
     * 
     */
    private final ScmRepositoryDefinition gitRepository;
    
    
    
    /**
     * @param _gitRepository
     */
    ScmKeySearchFilter(ScmRepositoryDefinition _gitRepository) {
    
    
        gitRepository = _gitRepository;
    }
    
    
    @Override
    public boolean match(final ScmRepositoryDefinition _object) {
    
    
        return gitRepository.getKey().equals(_object.getKey());
    }
}