package org.komea.event.factory;
import org.komea.product.database.dto.EventSimpleDto;
/**
 */
public class JenkinsEventFactory {
/**
 * Method sendLevelIndustrialisation.
 * @param _projectName String
 * @param _value double
 * @return EventSimpleDto
 */
public EventSimpleDto sendLevelIndustrialisation(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("build_industrialization");
event.setProvider("jenkins");
event.setProject(_projectName);
event.setMessage("Industrialization level for the project $project:$value");
event.setValue(_value);
return event;
}
/**
 * Method sendBuildComplete.
 * @param _projectName String
 * @param _value double
 * @param _branch java.lang.String
 * @return EventSimpleDto
 */
public EventSimpleDto sendBuildComplete(String _projectName,double _value,java.lang.String _branch) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("build_complete");
event.setProvider("jenkins");
event.setProject(_projectName);
event.setMessage("Build performed for the  project $project in $value min.");
event.setValue(_value);
event.setProperties(new java.util.HashMap());
event.getProperties().put("branch",_branch.toString());
return event;
}
/**
 * Method sendBuildFailed.
 * @param _projectName String
 * @param _value double
 * @param _branch java.lang.String
 * @return EventSimpleDto
 */
public EventSimpleDto sendBuildFailed(String _projectName,double _value,java.lang.String _branch) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("build_failed");
event.setProvider("jenkins");
event.setProject(_projectName);
event.setMessage("Build failed for the  project $project in $value min.");
event.setValue(_value);
event.setProperties(new java.util.HashMap());
event.getProperties().put("branch",_branch.toString());
return event;
}
/**
 * Method sendBuildInterrupted.
 * @param _projectName String
 * @param _value double
 * @param _branch java.lang.String
 * @return EventSimpleDto
 */
public EventSimpleDto sendBuildInterrupted(String _projectName,double _value,java.lang.String _branch) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("build_interrupted");
event.setProvider("jenkins");
event.setProject(_projectName);
event.setMessage("Build interrupted for the  project $project in $value min.");
event.setValue(_value);
event.setProperties(new java.util.HashMap());
event.getProperties().put("branch",_branch.toString());
return event;
}

}
