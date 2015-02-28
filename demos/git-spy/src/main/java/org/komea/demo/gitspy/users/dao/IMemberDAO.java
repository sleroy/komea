/**
 *
 */
package org.komea.demo.gitspy.users.dao;

import org.komea.demo.gitspy.users.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This interface defines the CRUD to manipulate members.
 * @author sleroy
 *
 *
 */
@RepositoryRestResource(path="/members", collectionResourceRel="members")
public interface IMemberDAO extends CrudRepository<Member, Integer> {

}
