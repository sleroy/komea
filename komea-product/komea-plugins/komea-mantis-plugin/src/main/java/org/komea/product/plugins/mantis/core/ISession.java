/*
 * Copyright 2005 Peter Lanser
 * MantisConnect is copyrighted to Victor Boctor
 * This program is distributed under the terms and conditions of the GPL
 * See LICENSE file for details.
 * For commercial applications to link with or modify MantisConnect, they
 * require the purchase of a MantisConnect commercial license.
 */

package org.komea.product.plugins.mantis.core;



import biz.futureware.mantis.rpc.soap.client.FilterData;
import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.IssueNoteData;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import biz.futureware.mantis.rpc.soap.client.ProjectAttachmentData;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import biz.futureware.mantis.rpc.soap.client.ProjectVersionData;



/**
 * @author Peter Lanser, planser@users.sourceforge.net
 */
public interface ISession
{
    
    
    public final static int VS_PRIVATE = 50;
    
    public final static int VS_PUBLIC  = 10;
    
    
    
    /**
     * Submit the specified issue.
     */
    public long addIssue(IssueData issue) throws JMTException;
    
    
    /**
     * Submit an issue attachment
     */
    public long addIssueAttachment(long issueId, String name, String fileType, byte[] content)
            throws JMTException;
    
    
    /**
     * Submit a new note.
     */
    public long addNote(long issueId, IssueNoteData note) throws JMTException;
    
    
    /**
     * Submit a project attachment
     */
    public long addProjectAttachment(
            long issueId,
            String name,
            String fileType,
            String title,
            String description,
            byte[] content) throws JMTException;
    
    
    /**
     * Delete the issue with the specified id.
     */
    public boolean deleteIssue(long issueId) throws JMTException;
    
    
    /**
     * Delete the issue attachment with the specified id.
     */
    public boolean deleteIssueAttachment(long attachmentId) throws JMTException;
    
    
    /**
     * Delete the note with the specified id.
     */
    public boolean deleteNote(long noteId) throws JMTException;
    
    
    /**
     * Delete the project attachment with the specified id.
     */
    public boolean deleteProjectAttachment(long attachmentId) throws JMTException;
    
    
    /**
     * Flush cached information
     */
    public void flush();
    
    
    /**
     * Get the list of projects that are accessible to the logged in user.
     */
    public ProjectData[] getAccessibleProjects() throws JMTException;
    
    
    /**
     * Get the latest submitted issue in the specified project.
     */
    public long getBiggestIssueId(long projectId) throws JMTException;
    
    
    /**
     * Get the categories belonging to the specified project.
     */
    public String[] getCategories(long projectId) throws JMTException;
    
    
    /**
     * Get the value for the specified configuration variable.
     */
    public String getConfigString(String configVar) throws JMTException;
    
    
    /**
     * Get the specified enumeration.
     */
    public ObjectRef[] getEnum(Enum enumeration) throws JMTException;
    
    
    /**
     * Get the specified enumeration.
     */
    public String getEnum(String enumeration) throws JMTException;
    
    
    /**
     * Get the filters defined for the specified project.
     */
    public FilterData[] getFilters(long projectId) throws JMTException;
    
    
    /**
     * Get the id of the issue with the specified summary.
     */
    public long getIdFromSummary(String summary) throws JMTException;
    
    
    /**
     * Get the issue with the specified id.
     */
    public IssueData getIssue(long issueId) throws JMTException;
    
    
    /**
     * Get the data for the specified issue attachment.
     */
    public byte[] getIssueAttachment(long attachmentId) throws JMTException;
    
    
    /**
     * Get all issues that match the specified filter.
     */
    public IssueData[] getIssues(long projectId, long filterId) throws JMTException;
    
    
    /**
     * Get issues that match the specified filter. Constrain the number of
     * issues to <code>limit</code>.
     */
    public IssueData[] getIssues(long projectId, long filterId, int limit) throws JMTException;
    
    
    /**
     * Get all issues that match the specified filter and paging details.
     */
    public IssueData[] getIssues(long projectId, long filterId, int pageNumber, int perPage)
            throws JMTException;
    
    
    /**
     * Get the data for the specified project attachment.
     */
    public byte[] getProjectAttachment(long attachmentId) throws JMTException;
    
    
    /**
     * Get the attachments that belong to the specified project.
     */
    public ProjectAttachmentData[] getProjectAttachments(long projectId) throws JMTException;
    
    
    /**
     * Get all issues that match the specified project.
     */
    public IssueData[] getProjectIssues(long projectId) throws JMTException;
    
    
    /**
     * Get the issues that match the specified project id and paging details
     * Constrain the number of issues to <code>limit</code>.
     */
    public IssueData[] getProjectIssues(long projectId, int limit) throws JMTException;
    
    
    /**
     * Get the issues that match the specified project id and paging details
     */
    public IssueData[] getProjectIssues(long projectId, int pageNumber, int perPage)
            throws JMTException;
    
    
    /**
     * Get the released versions that belong to the specified project.
     */
    public ProjectVersionData[] getReleasedVersions(long projectId) throws JMTException;
    
    
    /**
     * Get the unreleased version that belong to the specified project.
     */
    public ProjectVersionData[] getUnreleasedVersions(long projectId) throws JMTException;
    
    
    /**
     * Get Version of MantisConnect this session is connected to.
     */
    public String getVersion() throws JMTException;
    
    
    /**
     * Get the versions belonging to the specified project.
     */
    public ProjectVersionData[] getVersions(long projectId) throws JMTException;
    
    
    /**
     * Check there exists an issue with the specified id.
     */
    public boolean issueExists(long issueId) throws JMTException;
    
    
    /**
     * Create a new Issue with default values set.
     */
    public IssueData newIssue(long projectId) throws JMTException;
    
    
    /**
     * Create a new Note with default values set.
     */
    public IssueNoteData newNote(String text) throws JMTException;
    
    
    /**
     * Update issue.
     */
    public boolean updateIssue(IssueData issue) throws JMTException;
    
}
