
package org.komea.product.backend.service.kpi;



import org.komea.product.backend.service.business.IEntityWithKPI;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;



/**
 * This interface defines an adapter to
 * 
 * @author sleroy
 */
public interface IEntityWithKPIAdapter
{
    
    
    /**
     * Adapt a person into a entity with KPI.
     * 
     * @param _person
     *            the person
     * @return the person.
     */
    IEntityWithKPI<Person> adapt(Person _person);
    
    
    /**
     * Adapt a person into a entity with KPI.
     * 
     * @param _personGroup
     *            the person group.
     * @return the person group
     */
    IEntityWithKPI<PersonGroup> adapt(PersonGroup _personGroup);
    
    
    /**
     * Adapt a project into a entity with KPI.
     * 
     * @param _project
     *            the project
     * @return the project
     */
    IEntityWithKPI<Project> adapt(Project _project);
}
