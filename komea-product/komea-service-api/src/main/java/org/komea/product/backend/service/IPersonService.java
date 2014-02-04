
package org.komea.product.backend.service;


import java.util.List;

import org.komea.product.database.model.Person;

/**
 * Komea service to manage persons
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 4 févr. 2014
 */
public interface IPersonService
{
    
    /**
     * This method return the complete person list
     * 
     * @return
     */
    List<Person> getPersonList();
    //
}
