
package org.komea.product.backend.service.entities;


import java.util.List;

import org.komea.product.database.dto.PersonDto;

/**
 * Komea service to manage person
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 */
public interface IPersonService {
    
    /**
     * This method list all person
     * 
     * @return the person list
     */
    public abstract List<PersonDto> getPersonList();
    
}
