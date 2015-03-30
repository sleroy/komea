
package org.komea.connectors.git.impl;


import org.komea.connectors.git.IFileUpdate;

public class FileUpdate implements IFileUpdate
{

    private String path;
    private String oldPath;
    private int    numberOfAddedLines;
    private int    numberOfDeletedLines;

    public FileUpdate() {

    }

    @Override
    public int getNumberOfAddedLines() {

        return this.numberOfAddedLines;
    }

    @Override
    public int getNumberOfDeletedLines() {

        return this.numberOfDeletedLines;
    }

    public String getOldPath() {

        return this.oldPath;
    }

    @Override
    public String getPath() {

        return this.path;
    }

    public void setNumberOfAddedLines(final int numberOfAddedLines) {

        this.numberOfAddedLines = numberOfAddedLines;
    }

    public void setNumberOfDeletedLines(final int numberOfDeletedLines) {

        this.numberOfDeletedLines = numberOfDeletedLines;
    }

    public void setOldPath(final String oldPath) {

        this.oldPath = oldPath;
    }

    public void setPath(final String path) {

        this.path = path;
    }

    @Override
    public String toString() {

        return this.path;
    }
}
