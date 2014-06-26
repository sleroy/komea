/**
 *
 */

package org.komea.product.api.service.errors;



import org.komea.product.backend.exceptions.KomeaRuntimeException;



/**
 * This class defines a configuration problem in ldap plugin.
 *
 * @author sleroy
 */
public class KomeaLdapConfigurationException extends KomeaRuntimeException
{


    public KomeaLdapConfigurationException() {


        this("", null);
    }


    public KomeaLdapConfigurationException(final String _message) {


        this(_message, null);
    }


    public KomeaLdapConfigurationException(final String _message, final Throwable _e) {


        super("Problem with LDAP Configuration : " + _message, _e);
    }


    public KomeaLdapConfigurationException(final Throwable _cause) {


        this("", _cause);
    }

}
