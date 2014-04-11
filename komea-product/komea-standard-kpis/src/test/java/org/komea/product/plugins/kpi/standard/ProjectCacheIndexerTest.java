/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.EntityKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class ProjectCacheIndexerTest
{
    
    
    public Project newProject(final int _id, final String _projectKey) {
    
    
        final Project project = new Project();
        project.setId(_id);
        project.setProjectKey(_projectKey);
        project.setName(_projectKey);
        
        
        return project;
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.kpi.standard.ProjectCacheIndexer#getKey(org.komea.product.database.alert.IEvent)}.
     */
    @Test @Ignore
    public final void testfAIL() throws Exception {
    
    
        final Project newProject = newProject(1, "ABC");
        final Project newProject2 = newProject(2, "ABCD");
        final Project newProject3 = newProject(1, "ABCD");
        final Map<Project, Project> entityMap = new HashMap<Project, Project>();
        
        entityMap.put(newProject, newProject);
        entityMap.put(newProject2, newProject2);
        entityMap.put(newProject3, newProject3);
        
        assertEquals(3, entityMap.size());
        assertTrue(entityMap.containsKey(newProject));
        assertTrue(entityMap.containsKey(newProject2));
        assertTrue(entityMap.containsKey(newProject3));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.kpi.standard.ProjectCacheIndexer#getKey(org.komea.product.database.alert.IEvent)}.
     */
    @Test @Ignore
    public final void testGetKey() throws Exception {
    
    
        final ProjectCacheIndexer projectCacheIndexer = new ProjectCacheIndexer();
        final Project newProject = newProject(1, "ABC");
        final Project newProject2 = newProject(2, "ABCD");
        final Project newProject3 = newProject(1, "ABCD");
        final Map<EntityKey, Project> entityMap = new HashMap<EntityKey, Project>();
        final EntityKey key1 = projectCacheIndexer.getKey(newEvent(newProject));
        final EntityKey key2 = projectCacheIndexer.getKey(newEvent(newProject2));
        final EntityKey key3 = projectCacheIndexer.getKey(newEvent(newProject3));
        entityMap.put(key1, newProject);
        entityMap.put(key2, newProject2);
        entityMap.put(key3, newProject3);
        
        assertEquals(2, entityMap.size());
        assertEquals(key1, key3);
        assertTrue(entityMap.containsKey(key1));
        assertTrue(entityMap.containsKey(key2));
        assertTrue(entityMap.containsKey(key3));
        
        
    }
    
    
    /**
     * @param _newProject
     * @return
     */
    private IEvent newEvent(final Project _newProject) {
    
    
        final Event event = new Event();
        event.setProject(_newProject);
        return event;
    }
}
