/**
 *
 */
package org.komea.demo.gitspy.repository.dao;

import org.komea.demo.gitspy.repository.domain.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This interface defines the CRUD to manipulate repositories.
 * @author sleroy
 *
 */
@RepositoryRestResource(path="/repositories", collectionResourceRel="repositories")
public interface IRepositoryDAO extends CrudRepository<Repository, Integer> {

}
