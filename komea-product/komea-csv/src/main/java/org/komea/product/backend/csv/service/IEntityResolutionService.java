/**
 *
 */

package org.komea.product.backend.csv.service;



import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;



/**
 * @author sleroy
 */
public interface IEntityResolutionService
{


    IEntityService getEntityService();


    IPersonGroupService getPersonGroupService();


    IPersonService getPersonService();


    IProjectService getProjectService();
}
