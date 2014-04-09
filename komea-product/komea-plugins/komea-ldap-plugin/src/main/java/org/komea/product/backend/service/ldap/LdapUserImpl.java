
package org.komea.product.backend.service.ldap;



import java.util.Arrays;

import org.komea.product.api.service.ldap.LdapUser;



public class LdapUserImpl implements LdapUser
{
    
    
    private static final long serialVersionUID = 7487133273442955818L;
    
    private String            department;
    private String            email;
    private String            firstName;
    private String            groups[];
    private String            lastName;
    private String            password;
    private String            userName;
    
    
    
    @Override
    public String getDepartment() {
    
    
        return department;
    }
    
    
    @Override
    public String getEmail() {
    
    
        return email;
    }
    
    
    @Override
    public String getFirstName() {
    
    
        return firstName;
    }
    
    
    @Override
    public String[] getGroups() {
    
    
        return groups;
    }
    
    
    @Override
    public String getLastName() {
    
    
        return lastName;
    }
    
    
    @Override
    public String getPassword() {
    
    
        return password;
    }
    
    
    @Override
    public String getUserName() {
    
    
        return userName;
    }
    
    
    @Override
    public void setDepartment(final String department) {
    
    
        this.department = department;
    }
    
    
    @Override
    public void setEmail(final String email) {
    
    
        this.email = email;
    }
    
    
    @Override
    public void setFirstName(final String firstName) {
    
    
        this.firstName = firstName;
    }
    
    
    @Override
    public void setGroups(final String[] groups) {
    
    
        this.groups = groups;
    }
    
    
    @Override
    public void setLastName(final String lastName) {
    
    
        this.lastName = lastName;
    }
    
    
    @Override
    public void setPassword(final String password) {
    
    
        this.password = password;
    }
    
    
    @Override
    public void setUserName(final String userName) {
    
    
        this.userName = userName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "LdapUserImpl [department="
                + department + ", email=" + email + ", firstName=" + firstName + ", groups="
                + Arrays.toString(groups) + ", lastName=" + lastName + ", userName=" + userName
                + "]";
    }
}
