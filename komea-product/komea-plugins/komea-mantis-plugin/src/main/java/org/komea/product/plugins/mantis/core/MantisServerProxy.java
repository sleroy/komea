/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.core;



import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.plugins.mantis.api.IMantisServerProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import biz.futureware.mantis.rpc.soap.client.ProjectData;

import com.google.common.collect.Lists;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class MantisServerProxy implements IMantisServerProxy
{
    
    
    /**
     * @author sleroy
     */
    private final class ObjectRefToString implements Converter<ObjectRef, String>
    {
        
        
        @Override
        public String convert(final ObjectRef _from) {
        
        
            return _from.getName();
        }
    }
    
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("bugzilla-proxy");
    private final ISession      conn;
    
    
    
    /**
     * Constructor for MantisServerProxy.
     * 
     * @param _iSession
     *            Session
     */
    public MantisServerProxy(final ISession _iSession) {
    
    
        Validate.notNull(_iSession, "Could not connect to the server");
        conn = _iSession;
    }
    
    
    /**
     * Method close.
     * 
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() {
    
    
        // Nothing to do
    }
    
    
    /**
     * Method getListBugs.
     * 
     * @param _productName
     * @return List<BugzillaBug>
     * @see org.komea.backend.IMantisServerProxy.bugzilla.api.IBugZillaServerProxy#getListBugs(String)
     */
    @Override
    public List<IssueData> getBugs(final String _productName) {
    
    
        final List<IssueData> IssueDatas = Lists.newArrayList();
        try {
            final ProjectData project = findProject(_productName);
            if (project == null) {
                return Collections.emptyList();
            }
            
            final IssueData[] issues = conn.getProjectIssues(project.getId().longValue());
            for (final IssueData bug : issues) {
                IssueDatas.add(bug);
            }
            
        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return IssueDatas;
    }
    
    
    @Override
    public List<String> getPriorities() {
    
    
        return convertEnumIntoString(Enum.PRIORITIES);
    }
    
    
    @Override
    public List<String> getProductNames() {
    
    
        final List<String> productNames = Lists.newArrayList();
        try {
            for (final ProjectData projectData : conn.getAccessibleProjects()) {
                
                productNames.add(projectData.getName());
                
            }
            
        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return productNames;
    }
    
    
    @Override
    public List<String> getSeverities() {
    
    
        return convertEnumIntoString(Enum.SEVERITIES);
    }
    
    
    @Override
    public boolean testConnexion() {
    
    
        return conn != null;
    }
    
    
    private List<String> convertEnumIntoString(final Enum enumeration) {
    
    
        ObjectRef[] severities;
        try {
            
            severities = conn.getEnum(enumeration);
        } catch (final JMTException e) {
            return Collections.EMPTY_LIST;
        }
        final Converter<ObjectRef, String> converter = new ObjectRefToString();
        final List<ObjectRef> list = Lists.newArrayList(severities);
        final List<String> severitiesNames = CollectionUtil.convertAll(list, converter);
        return severitiesNames;
    }
    
    
    /**
     * @param _productName
     * @return
     * @throws JMTException
     */
    private ProjectData findProject(final String _productName) throws JMTException {
    
    
        for (final ProjectData project : conn.getAccessibleProjects()) {
            if (project.getName().equalsIgnoreCase(_productName)) {
                return project;
            }
            
        }
        return null;
    }
    
    
}
