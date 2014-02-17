
package org.komea.product.backend.auth;



import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
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



@Service(value = "komeaUserAuthService")
public class UserAuthenticationService implements UserDetailsService
{
    
    
    @Autowired
    private PersonDao           personDAO;
    
    @Autowired
    private PersonRoleDao       personRoleDAO;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationService.class);
    
    
    
    public UserAuthenticationService() {
    
    
        super();
    }
    
    
    @Override
    public UserDetails loadUserByUsername(final String _username) throws UsernameNotFoundException {
    
    
        final PersonCriteria personByLoginCriteria = new PersonCriteria();
        personByLoginCriteria.createCriteria().andLoginEqualTo(_username);
        final Person requestedLoginUsers =
                CollectionUtil.singleOrNull(personDAO.selectByCriteria(personByLoginCriteria));
        
        if (requestedLoginUsers == null) { throw new UsernameNotFoundException(
                "Invalid username/password."); }
        final PersonRoleCriteria personRoleCriteria = new PersonRoleCriteria();
        personRoleCriteria.createCriteria().andIdEqualTo(requestedLoginUsers.getIdPersonRole());
        final PersonRole personRole =
                CollectionUtil.singleOrNull(personRoleDAO.selectByCriteria(personRoleCriteria));
        String right = "";
        if (personRole != null) {
            right = personRole.getName();
        } else {
            throw new UsernameNotFoundException("Invalid username/password.");
        }
        LOGGER.info("-----AUTH----- User authentication requested {}", _username);
        return new User(requestedLoginUsers.getLogin(), requestedLoginUsers.getPassword(),
                AuthorityUtils.createAuthorityList(right));
    }
}
