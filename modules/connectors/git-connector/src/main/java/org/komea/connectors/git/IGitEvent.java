
package org.komea.connectors.git;


public interface IGitEvent
{

    String PROVIDER = "git";
    String COMMIT   = "commit";
    String UPDATE   = "file_update";
    String TAG      = "tag";
}
