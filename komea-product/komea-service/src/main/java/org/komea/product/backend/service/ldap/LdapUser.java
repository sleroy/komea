
package org.komea.product.backend.service.ldap;



public interface LdapUser
{
    
    
    String getDepartment();
    
    
    String getEmail();
    
    
    String getFirstName();
    
    
    String[] getGroups();
    
    
    String getLastName();
    
    
    String getPassword();
    
    
    String getUserName();
    
    
    void setDepartment(String departement);
    
    
    void setEmail(String email);
    
    
    void setFirstName(String firstName);
    
    
    void setGroups(String[] groups);
    
    
    void setLastName(String lastName);
    
    
    void setPassword(String password);
    
    
    void setUserName(String userName);
}
