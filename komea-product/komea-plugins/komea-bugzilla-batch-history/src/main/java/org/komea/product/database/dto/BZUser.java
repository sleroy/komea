/**
 *
 */

package org.komea.product.database.dto;

import java.io.Serializable;



/**
 * @author sleroy
 */
public class BZUser implements Serializable
{


    private String  login_name;
    
    
    private Integer userid;



    public String getLogin_name() {
    
    
        return login_name;
    }


    public Integer getUserid() {
    
    
        return userid;
    }


    public void setLogin_name(final String _login_name) {
    
    
        login_name = _login_name;
    }


    public void setUserid(final Integer _userid) {
    
    
        userid = _userid;
    }
    
}
