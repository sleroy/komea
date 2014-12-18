/**
 *
 */

package org.komea.connectors.git.impl;


import java.io.File;

/**
 * This class defines an scm repository basic attributes.
 *
 * @author sleroy
 */
public class ScmRepositoryDefinition
{

    private String userName;
    private String password;
    private String url;
    private File   cloneDirectory;

    private String name;

    public ScmRepositoryDefinition() {

    }

    public ScmRepositoryDefinition(final String url, final String name) {

        super();
        this.url = url;

        this.name = name;
    }

    public File getCloneDirectory() {

        return this.cloneDirectory;
    }

    public String getName() {

        return this.name;
    }

    public String getPassword() {

        return this.password;
    }

    public String getUrl() {

        return this.url;
    }

    public String getUserName() {

        return this.userName;
    }

    public void setCloneDirectory(final File cloneDirectory) {

        this.cloneDirectory = cloneDirectory;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public void setPassword(final String password) {

        this.password = password;
    }

    public void setUrl(final String url) {

        this.url = url;
    }

    public void setUserName(final String userName) {

        this.userName = userName;
    }

}
