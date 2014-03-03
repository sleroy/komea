package org.komea.event.factory;
import org.komea.product.database.dto.EventSimpleDto;
public class SonarEventFactory {
public EventSimpleDto sendAuditSuccess(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("audit_success");
event.setProvider("sonar");
event.setProject(_projectName);
event.setMessage("Sonar audit success for the project $project.");
event.setValue(_value);
return event;
}
public EventSimpleDto sendAuditStarted(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("audit_started");
event.setProvider("sonar");
event.setProject(_projectName);
event.setMessage("Sonar audit started for the project $project");
event.setValue(_value);
return event;
}
public EventSimpleDto sendMetricValue(String _projectName,double _value,java.lang.String _metricName) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("metric_value");
event.setProvider("sonar");
event.setProject(_projectName);
event.setMessage("Metric ${properties['metricName']} has value $value in project $project");
event.setValue(_value);
event.setProperties(new java.util.HashMap());
event.getProperties().put("metricName",_metricName.toString());
return event;
}

}
