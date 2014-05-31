/*
 * Copyright 2005 Peter Lanser
 * MantisConnect is copyrighted to Victor Boctor
 * This program is distributed under the terms and conditions of the GPL
 * See LICENSE file for details.
 * For commercial applications to link with or modify MantisConnect, they
 * require the purchase of a MantisConnect commercial license.
 */

package org.komea.product.plugins.mantis.core;



import java.math.BigInteger;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.xml.rpc.ServiceException;

import biz.futureware.mantis.rpc.soap.client.FilterData;
import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.IssueNoteData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import biz.futureware.mantis.rpc.soap.client.ProjectAttachmentData;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import biz.futureware.mantis.rpc.soap.client.ProjectVersionData;



/**
 * <p>
 * Default implementation for a Session which delegates all operations to axis.
 * </p>
 * <p>
 * The following information is cached:
 * <ul>
 * <li>Version info</li>
 * <li>All enumerations</li>
 * <li>Projects</li>
 * <li>Categories (per project)</li>
 * <li>Versions (per project - released and future)</li>
 * <li>Filters (per project)</li>
 * <li>Config-Strings</li>
 * </ul>
 * </p>
 * 
 * @author Peter Lanser, planser@users.sourceforge.net
 */
public class Session implements ISession
{
    
    
    private final HashMap         cachedCategories         = new HashMap();
    
    private final HashMap         cachedConfigStrings      = new HashMap();
    
    private final HashMap         cachedEnums              = new HashMap();
    
    private final HashMap         cachedFilters            = new HashMap();
    
    private final HashMap         cachedNamedEnums         = new HashMap();
    
    private ProjectData[]         cachedProjectData        = null;
    
    private final HashMap         cachedReleasedVersions   = new HashMap();
    
    private final HashMap         cachedUnreleasedVersions = new HashMap();
    
    private String                cachedVersion            = null;
    
    private final HashMap         cachedVersions           = new HashMap();
    
    private MantisConnectPortType portType;
    
    private final String          pwd;
    
    private final URL             url;
    
    private final String          user;
    
    
    
    public Session(final URL url, final String user, final String pwd) throws JMTException {
    
    
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        try {
            portType = new MantisConnectLocator().getMantisConnectPort(url);
        } catch (final ServiceException e) {
            throw new JMTException(e);
        }
    }
    
    
    public long addIssue(final IssueData issue) throws JMTException {
    
    
        try {
            return toLong(getPortType().mc_issue_add(getUser(), getPwd(), issue));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public long addIssueAttachment(
            final long issueId,
            final String name,
            final String fileType,
            final byte[] content) throws JMTException {
    
    
        try {
            return portType.mc_issue_attachment_add(getUser(), getPwd(), toBigInt(issueId), name,
                    fileType, content).longValue();
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    public long addNote(final long issueId, final IssueNoteData note) throws JMTException {
    
    
        try {
            return toLong(getPortType().mc_issue_note_add(getUser(), getPwd(), toBigInt(issueId),
                    note));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public long addProjectAttachment(
            final long projectId,
            final String name,
            final String title,
            final String description,
            final String fileType,
            final byte[] content) throws JMTException {
    
    
        try {
            return portType.mc_project_attachment_add(getUser(), getPwd(), toBigInt(projectId),
                    name, title, description, fileType, content).longValue();
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public boolean deleteIssue(final long issueId) throws JMTException {
    
    
        try {
            return getPortType().mc_issue_delete(getUser(), getPwd(), toBigInt(issueId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public boolean deleteIssueAttachment(final long attachmentId) throws JMTException {
    
    
        try {
            return portType.mc_issue_attachment_delete(getUser(), getPwd(), toBigInt(attachmentId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public boolean deleteNote(final long noteId) throws JMTException {
    
    
        try {
            return portType.mc_issue_note_delete(getUser(), getPwd(), toBigInt(noteId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public boolean deleteProjectAttachment(final long attachmentId) throws JMTException {
    
    
        try {
            return portType.mc_project_attachment_delete(getUser(), getPwd(),
                    toBigInt(attachmentId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public void flush() {
    
    
        cachedEnums.clear();
        cachedNamedEnums.clear();
        cachedVersion = null;
        cachedProjectData = null;
        cachedCategories.clear();
        cachedVersions.clear();
        cachedReleasedVersions.clear();
        cachedUnreleasedVersions.clear();
        cachedFilters.clear();
        cachedConfigStrings.clear();
    }
    
    
    @Override
    public ProjectData[] getAccessibleProjects() throws JMTException {
    
    
        ProjectData[] result = getCachedAccessibleProjects();
        if (result == null) {
            try {
                result = getPortType().mc_projects_get_user_accessible(getUser(), getPwd());
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedProjectData = result;
        }
        return result;
    }
    
    
    @Override
    public long getBiggestIssueId(final long projectId) throws JMTException {
    
    
        try {
            return toLong(getPortType().mc_issue_get_biggest_id(getUser(), getPwd(),
                    toBigInt(projectId)));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public String[] getCategories(final long projectId) throws JMTException {
    
    
        String[] result = getCachedCategories(projectId);
        if (result == null) {
            try {
                result =
                        getPortType().mc_project_get_categories(getUser(), getPwd(),
                                toBigInt(projectId));
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedCategories.put(new Long(projectId), result);
        }
        return result;
    }
    
    
    @Override
    public String getConfigString(final String configVar) throws JMTException {
    
    
        String result = getCachedConfigString(configVar);
        if (result == null) {
            try {
                result = getPortType().mc_config_get_string(getUser(), getPwd(), configVar);
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedConfigStrings.put(configVar, result);
        }
        return result;
    }
    
    
    @Override
    public ObjectRef[] getEnum(final Enum enumeration) throws JMTException {
    
    
        ObjectRef[] result = getCachedEnum(enumeration);
        if (result == null) {
            try {
                if (enumeration == Enum.ACCESS_LEVELS) {
                    result = getPortType().mc_enum_access_levels(getUser(), getPwd());
                } else if (enumeration == Enum.CUSTOM_FIELD_TYPES) {
                    result = getPortType().mc_enum_custom_field_types(getUser(), getPwd());
                } else if (enumeration == Enum.ETAS) {
                    result = getPortType().mc_enum_etas(getUser(), getPwd());
                } else if (enumeration == Enum.PRIORITIES) {
                    result = getPortType().mc_enum_priorities(getUser(), getPwd());
                } else if (enumeration == Enum.PROJECT_STATUS) {
                    result = getPortType().mc_enum_project_status(getUser(), getPwd());
                } else if (enumeration == Enum.PROJECT_VIEW_STATES) {
                    result = getPortType().mc_enum_project_view_states(getUser(), getPwd());
                } else if (enumeration == Enum.PROJECTIONS) {
                    result = getPortType().mc_enum_projections(getUser(), getPwd());
                } else if (enumeration == Enum.REPRODUCIBILITIES) {
                    result = getPortType().mc_enum_reproducibilities(getUser(), getPwd());
                } else if (enumeration == Enum.RESOLUTIONS) {
                    result = getPortType().mc_enum_resolutions(getUser(), getPwd());
                } else if (enumeration == Enum.SEVERITIES) {
                    result = getPortType().mc_enum_severities(getUser(), getPwd());
                } else if (enumeration == Enum.STATUS) {
                    result = getPortType().mc_enum_status(getUser(), getPwd());
                } else {
                    result = getPortType().mc_enum_view_states(getUser(), getPwd());
                }
                cachedEnums.put(enumeration, result);
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
        }
        return result;
    }
    
    
    @Override
    public String getEnum(final String enumeration) throws JMTException {
    
    
        String result = getCachedNamedEnum(enumeration);
        if (result == null) {
            try {
                result = getPortType().mc_enum_get(getUser(), getPwd(), enumeration);
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedNamedEnums.put(enumeration, result);
        }
        return result;
    }
    
    
    @Override
    public FilterData[] getFilters(final long projectId) throws JMTException {
    
    
        FilterData[] result = getCachedFilters(projectId);
        if (result == null) {
            try {
                result = getPortType().mc_filter_get(getUser(), getPwd(), toBigInt(projectId));
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedFilters.put(new Long(projectId), result);
        }
        return result;
    }
    
    
    @Override
    public long getIdFromSummary(final String summary) throws JMTException {
    
    
        try {
            return toLong(getPortType().mc_issue_get_id_from_summary(getUser(), getPwd(), summary));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public IssueData getIssue(final long issueId) throws JMTException {
    
    
        try {
            return getPortType().mc_issue_get(getUser(), getPwd(), toBigInt(issueId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public byte[] getIssueAttachment(final long attachmentId) throws JMTException {
    
    
        try {
            return portType.mc_issue_attachment_get(getUser(), getPwd(), toBigInt(attachmentId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public IssueData[] getIssues(final long projectId, final long filterId) throws JMTException {
    
    
        try {
            return getPortType().mc_filter_get_issues(getUser(), getPwd(), toBigInt(projectId),
                    toBigInt(filterId), null, null);
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public IssueData[] getIssues(final long projectId, final long filterId, final int limit)
            throws JMTException {
    
    
        return getIssues(projectId, filterId, 0, limit);
    }
    
    
    @Override
    public IssueData[] getIssues(
            final long projectId,
            final long filterId,
            final int pageNumber,
            final int perPage) throws JMTException {
    
    
        try {
            return getPortType().mc_filter_get_issues(getUser(), getPwd(), toBigInt(projectId),
                    toBigInt(filterId), toBigInt(pageNumber), toBigInt(perPage));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public byte[] getProjectAttachment(final long attachmentId) throws JMTException {
    
    
        try {
            return portType.mc_project_attachment_get(getUser(), getPwd(), toBigInt(attachmentId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public ProjectAttachmentData[] getProjectAttachments(final long projectId) throws JMTException {
    
    
        try {
            return portType.mc_project_get_attachments(getUser(), getPwd(), toBigInt(projectId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public IssueData[] getProjectIssues(final long projectId) throws JMTException {
    
    
        try {
            return getPortType().mc_project_get_issues(getUser(), getPwd(), toBigInt(projectId),
                    null, null);
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public IssueData[] getProjectIssues(final long projectId, final int limit) throws JMTException {
    
    
        return getProjectIssues(projectId, 0, limit);
    }
    
    
    @Override
    public IssueData[] getProjectIssues(
            final long projectId,
            final int pageNumber,
            final int perPage) throws JMTException {
    
    
        try {
            return portType.mc_project_get_issues(getUser(), getPwd(), toBigInt(projectId),
                    toBigInt(pageNumber), toBigInt(perPage));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public ProjectVersionData[] getReleasedVersions(final long projectId) throws JMTException {
    
    
        ProjectVersionData[] result = getCachedReleasedVersions(projectId);
        if (result == null) {
            try {
                result =
                        getPortType().mc_project_get_released_versions(getUser(), getPwd(),
                                toBigInt(projectId));
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedReleasedVersions.put(new Long(projectId), result);
        }
        return result;
    }
    
    
    @Override
    public ProjectVersionData[] getUnreleasedVersions(final long projectId) throws JMTException {
    
    
        ProjectVersionData[] result = getCachedUnreleasedVersions(projectId);
        if (result == null) {
            try {
                result =
                        getPortType().mc_project_get_unreleased_versions(getUser(), getPwd(),
                                toBigInt(projectId));
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedUnreleasedVersions.put(new Long(projectId), result);
        }
        return result;
    }
    
    
    @Override
    public String getVersion() throws JMTException {
    
    
        if (cachedVersion == null) {
            try {
                cachedVersion = getPortType().mc_version();
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
        }
        return cachedVersion;
    }
    
    
    @Override
    public ProjectVersionData[] getVersions(final long projectId) throws JMTException {
    
    
        ProjectVersionData[] result = getCachedVersions(projectId);
        if (result == null) {
            try {
                result =
                        getPortType().mc_project_get_versions(getUser(), getPwd(),
                                toBigInt(projectId));
            } catch (final RemoteException e) {
                throw new JMTException(e);
            }
            cachedVersions.put(new Long(projectId), result);
        }
        return result;
    }
    
    
    @Override
    public boolean issueExists(final long issueId) throws JMTException {
    
    
        try {
            return getPortType().mc_issue_exists(getUser(), getPwd(), toBigInt(issueId));
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    @Override
    public IssueData newIssue(final long projectId) throws JMTException {
    
    
        final IssueData issue = new IssueData();
        issue.setSeverity(getDefaultIssueSeverity());
        issue.setPriority(getDefaultIssuePriority());
        issue.setView_state(getDefaultIssueViewState());
        issue.setProject(getProject(projectId));
        return issue;
    }
    
    
    @Override
    public IssueNoteData newNote(final String text) throws JMTException {
    
    
        final IssueNoteData note = new IssueNoteData();
        note.setText(text);
        note.setView_state(getDefaultNoteViewState());
        return note;
    }
    
    
    public boolean updateIssue(final IssueData issue) throws JMTException {
    
    
        try {
            return portType.mc_issue_update(getUser(), getPwd(), issue.getId(), issue);
        } catch (final RemoteException e) {
            throw new JMTException(e);
        }
    }
    
    
    private ObjectRef getDefaultIssueViewState() throws JMTException {
    
    
        final ObjectRef[] viewStates = getEnum(Enum.VIEW_STATES);
        final long viewState = Long.parseLong(getConfigString("default_bug_view_status"));
        for (int i = 0; i < viewStates.length; i++) {
            if (viewStates[i].getId().longValue() == viewState) {
                return viewStates[i];
            }
        }
        return null;
    }
    
    
    private ObjectRef getDefaultNoteViewState() throws JMTException {
    
    
        final ObjectRef[] viewStates = getEnum(Enum.VIEW_STATES);
        final long viewState = Long.parseLong(getConfigString("default_bugnote_view_status"));
        for (int i = 0; i < viewStates.length; i++) {
            if (viewStates[i].getId().longValue() == viewState) {
                return viewStates[i];
            }
        }
        return null;
    }
    
    
    protected ProjectData[] getCachedAccessibleProjects() {
    
    
        return cachedProjectData;
    }
    
    
    protected String[] getCachedCategories(final long projectId) {
    
    
        return (String[]) cachedCategories.get(new Long(projectId));
    }
    
    
    protected String getCachedConfigString(final String configVar) {
    
    
        return (String) cachedConfigStrings.get(configVar);
    }
    
    
    protected ObjectRef[] getCachedEnum(final Enum enumeration) {
    
    
        return (ObjectRef[]) cachedEnums.get(enumeration);
    }
    
    
    protected FilterData[] getCachedFilters(final long projectId) {
    
    
        return (FilterData[]) cachedFilters.get(new Long(projectId));
    }
    
    
    protected String getCachedNamedEnum(final String enumeration) {
    
    
        return (String) cachedNamedEnums.get(enumeration);
    }
    
    
    protected ProjectVersionData[] getCachedReleasedVersions(final long projectId) {
    
    
        return (ProjectVersionData[]) cachedReleasedVersions.get(new Long(projectId));
    }
    
    
    protected ProjectVersionData[] getCachedUnreleasedVersions(final long projectId) {
    
    
        return (ProjectVersionData[]) cachedUnreleasedVersions.get(new Long(projectId));
    }
    
    
    protected ProjectVersionData[] getCachedVersions(final long projectId) {
    
    
        return (ProjectVersionData[]) cachedVersions.get(new Long(projectId));
    }
    
    
    protected ObjectRef getDefaultIssuePriority() throws JMTException {
    
    
        final ObjectRef[] priorities = getEnum(Enum.PRIORITIES);
        final long priority = Long.parseLong(getConfigString("default_bug_priority"));
        for (int i = 0; i < priorities.length; i++) {
            if (priorities[i].getId().longValue() == priority) {
                return priorities[i];
            }
        }
        return null;
    }
    
    
    protected ObjectRef getDefaultIssueSeverity() throws JMTException {
    
    
        final ObjectRef[] severities = getEnum(Enum.SEVERITIES);
        final long severity = Long.parseLong(getConfigString("default_bug_severity"));
        for (int i = 0; i < severities.length; i++) {
            if (severities[i].getId().longValue() == severity) {
                return severities[i];
            }
        }
        return null;
    }
    
    
    protected MantisConnectPortType getPortType() {
    
    
        return portType;
    }
    
    
    protected ObjectRef getProject(final long projectId) throws JMTException {
    
    
        final ProjectData[] projects = getAccessibleProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId().longValue() == projectId) {
                return new ObjectRef(projects[i].getId(), projects[i].getName());
            }
        }
        return null;
    }
    
    
    protected String getPwd() {
    
    
        return pwd;
    }
    
    
    protected URL getUrl() {
    
    
        return url;
    }
    
    
    protected String getUser() {
    
    
        return user;
    }
    
    
    protected BigInteger toBigInt(final long i) {
    
    
        return BigInteger.valueOf(i);
    }
    
    
    protected long toLong(final BigInteger i) {
    
    
        return i.longValue();
    }
    
}
