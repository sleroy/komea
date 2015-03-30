
package org.komea.connectors.git;


public interface IFileUpdate
{

    int getNumberOfAddedLines();

    int getNumberOfDeletedLines();
    String getPath();
    
    String getOldPath();

}
