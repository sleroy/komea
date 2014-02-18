package org.komea.event.factory;
import org.komea.product.database.dto.EventSimpleDto;
/**
 */
public class SonarEventFactory {
/**
 * Method sendAuditSuccess.
 * @param _projectName String
 * @param _value double
 * @return EventSimpleDto
 */
public EventSimpleDto sendAuditSuccess(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Sonar audit success for the project $project.");
event.setValue(_value);
return event;
}
/**
 * Method sendAuditFailed.
 * @param _projectName String
 * @param _value double
 * @return EventSimpleDto
 */
public EventSimpleDto sendAuditFailed(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Sonar audit failed for the project $project");
event.setValue(_value);
return event;
}
/**
 * Method sendMetricValue.
 * @param _projectName String
 * @param _value double
 * @param _metricName java.lang.String
 * @return EventSimpleDto
 */
public EventSimpleDto sendMetricValue(String _projectName,double _value,java.lang.String _metricName) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Metric ${properties['metricName']} has value $value in project $project");
event.setValue(_value);
event.setProperties(new java.util.HashMap());
event.getProperties().put("metricName",_metricName);
return event;
}

}
