
package org.komea.event.factory;



import org.komea.product.database.dto.EventSimpleDto;



public class JenkinsEventFactory
{
    
    
    public EventSimpleDto sendBuildComplete(
            final String _projectName,
            final double _value,
            final java.lang.String _branch) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setEventType("build_complete");
        event.setProvider("jenkins");
        event.setProject(_projectName);
        event.setMessage("Build performed for the  project $project in $value min.");
        event.setValue(_value);
        event.setProperties(new java.util.HashMap());
        event.getProperties().put("branch", _branch.toString());
        return event;
    }
    
    
    public EventSimpleDto sendBuildFailed(
            final String _projectName,
            final double _value,
            final java.lang.String _branch) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setEventType("build_failed");
        event.setProvider("jenkins");
        event.setProject(_projectName);
        event.setMessage("Build failed for the  project $project in $value min.");
        event.setValue(_value);
        event.setProperties(new java.util.HashMap());
        event.getProperties().put("branch", _branch.toString());
        return event;
    }
    
    
    public EventSimpleDto sendBuildInterrupted(
            final String _projectName,
            final double _value,
            final java.lang.String _branch) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setEventType("build_interrupted");
        event.setProvider("jenkins");
        event.setProject(_projectName);
        event.setMessage("Build interrupted for the  project $project in $value min.");
        event.setValue(_value);
        event.setProperties(new java.util.HashMap());
        event.getProperties().put("branch", _branch.toString());
        return event;
    }
    
    
    public EventSimpleDto sendLevelIndustrialisation(final String _projectName, final double _value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setEventType("build_industrialization");
        event.setProvider("jenkins");
        event.setProject(_projectName);
        event.setMessage("Industrialization level for the project $project:$value");
        event.setValue(_value);
        return event;
    }
    
}
