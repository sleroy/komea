public class BugZillaEventBuilder {
public EventSimpleDTO sendTotalBugs(String _projectName,String _groupName,String _message,double _value,URL _url,List<String> _userNames) {EventSimpleDTO event = new EventSimpleDTO();
event.setProject(_projectName);
event.setUserGroup(_groupName);
event.setMessage(_message);
event.setValue(_value);
event.setURL(_url.toString());
event.setPersons(_users);
return event;
}

}
