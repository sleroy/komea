package org.komea.event.factory;
import org.komea.product.database.dto.EventSimpleDto;
public class ProjectEventFactory {
public EventSimpleDto sendProjectCharge(String _projectName,java.lang.Double _consumed,java.lang.Double _estimated,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Project charges are estimated  and planned, deviation #estimated/#planned/#consumed");
event.setProperties(new java.util.HashMap());
event.getProperties().put("consumed",_consumed.toString());
event.getProperties().put("estimated",_estimated.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendProjectFunctionalities(String _projectName,java.lang.Double _estimated,java.lang.Double _done,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Project functionalities are estimated  and planned, deviation #estimated/#planned/#done");
event.setProperties(new java.util.HashMap());
event.getProperties().put("estimated",_estimated.toString());
event.getProperties().put("done",_done.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendProjectTime(String _projectName,java.lang.Double _estimated,java.lang.Double _done,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Project ETA are estimated  and planned, deviation #estimated/#planned");
event.setProperties(new java.util.HashMap());
event.getProperties().put("estimated",_estimated.toString());
event.getProperties().put("done",_done.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendDelayedStories(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Number of delayed stories in project ");
event.setValue(_value);
return event;
}
public EventSimpleDto sendUpdatedStories(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Number of updated stories in project ");
event.setValue(_value);
return event;
}
public EventSimpleDto sendActivityTeam(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Activity rate of the team for the project $project is $value");
event.setValue(_value);
return event;
}
public EventSimpleDto sendMaintFunctionalities(String _projectName,java.lang.Double _done,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Number of maintenancy functionalities for the project $project");
event.setProperties(new java.util.HashMap());
event.getProperties().put("done",_done.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendEvolutionFunctionalities(String _projectName,java.lang.Double _done,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Number of evolution functionalities for the project $project");
event.setProperties(new java.util.HashMap());
event.getProperties().put("done",_done.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendBugFixesFunctionalities(String _projectName,java.lang.Double _done,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Number of bug fixes functionalities for the project $project");
event.setProperties(new java.util.HashMap());
event.getProperties().put("done",_done.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendInnovationFunctionalities(String _projectName,java.lang.Double _done,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Number of innovation functionalities for the project $project");
event.setProperties(new java.util.HashMap());
event.getProperties().put("done",_done.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendReleasedStory(String _projectName,double _value) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Released story");
event.setValue(_value);
return event;
}
public EventSimpleDto sendUserValue(String _projectName,java.lang.Double _done,java.lang.Double _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("User value planned/performed");
event.setProperties(new java.util.HashMap());
event.getProperties().put("done",_done.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}
public EventSimpleDto sendStoryStatus(String _projectName,java.lang.String _status,java.lang.String _name) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("Story status");
event.setProperties(new java.util.HashMap());
event.getProperties().put("status",_status.toString());
event.getProperties().put("name",_name.toString());
return event;
}
public EventSimpleDto sendUserValue(String _projectName,java.lang.String _performed,java.lang.String _planned) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("User value planned/performed");
event.setProperties(new java.util.HashMap());
event.getProperties().put("performed",_performed.toString());
event.getProperties().put("planned",_planned.toString());
return event;
}

}
