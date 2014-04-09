
package org.komea.product.backend.auth;



import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 */
@Service(value = "komeaUserAuthService")
public class UserAuthenticationService implements UserDetailsService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("userauth");
    
    
    @Autowired
    private IPersonService      personDAO;
    
    
    @Autowired
    private IPersonRoleService  personRoleDAO;
    
    
    
    public UserAuthenticationService() {
    
    
        super();
    }
    
    
    /**
     * Method loadUserByUsername.
     * 
     * @param _username
     *            String
     * @return UserDetails
     * @throws UsernameNotFoundException
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(String)
     */
    @Override
    public UserDetails loadUserByUsername(final String _username) throws UsernameNotFoundException {
    
    
        LOGGER.debug("Lookup on username : {}", _username);
        final PersonCriteria personByLoginCriteria = new PersonCriteria();
        personByLoginCriteria.createCriteria().andLoginEqualTo(_username);
        final Person requestedPerson =
                CollectionUtil.singleOrNull(personDAO.selectByCriteria(personByLoginCriteria));
        
        if (requestedPerson == null) {
            LOGGER.warn("User {} does not exist.", _username);
            throw new UsernameNotFoundException("Invalid username/password.");
        }
        if (!requestedPerson.isAssociatedToRole()) {
            LOGGER.error("User {} is without any role.", _username);
            throw new UsernameNotFoundException(
                    "An user without priviledge cannot be authenticated");
        }
        
        final PersonRoleCriteria personRoleCriteria = new PersonRoleCriteria();
        personRoleCriteria.createCriteria().andIdEqualTo(requestedPerson.getIdPersonRole());
        final PersonRole personRole =
                CollectionUtil.singleOrNull(personRoleDAO.selectByCriteria(personRoleCriteria));
        String right = "";
        if (personRole != null) {
            right = personRole.getRoleKey();
        } else {
            throw new UsernameNotFoundException(
                    "Invalid username/password, this user does not have role.");
        }
        LOGGER.trace("-----AUTH----- LdapUser authentication requested {}", _username);
        return new User(requestedPerson.getLogin(), requestedPerson.getPassword(),
                AuthorityUtils.createAuthorityList(right));
    }
    
    
    /**
     * @param _personDAO
     *            the personDAO to set
     */
    public void setPersonDAO(final IPersonService _personDAO) {
    
    
        personDAO = _personDAO;
    }
    
    
    /**
     * @param _personRoleDAO
     *            the personRoleDAO to set
     */
    public void setPersonRoleDAO(final IPersonRoleService _personRoleDAO) {
    
    
        personRoleDAO = _personRoleDAO;
    }
}
