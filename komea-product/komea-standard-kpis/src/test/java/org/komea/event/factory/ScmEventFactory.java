package org.komea.event.factory;
import org.komea.product.database.dto.EventSimpleDto;
/**
 */
public class ScmEventFactory {
/**
 * Method sendCommit.
 * @param _projectName String
 * @param _code_files java.lang.String
 * @param _num_files java.lang.String
 * @param _author java.lang.String
 * @param _num_lines java.lang.Integer
 * @param _message_length java.lang.String
 * @param _branch java.lang.String
 * @param _type java.lang.String
 * @param _misc_files java.lang.String
 * @param _test_files java.lang.String
 * @return EventSimpleDto
 */
public EventSimpleDto sendCommit(String _projectName,java.lang.String _code_files,java.lang.String _num_files,java.lang.String _author,java.lang.Integer _num_lines,java.lang.String _message_length,java.lang.String _branch,java.lang.String _type,java.lang.String _misc_files,java.lang.String _test_files) {EventSimpleDto event = new EventSimpleDto();
event.setProject(_projectName);
event.setMessage("New commit for the projet $project.");
event.setProperties(new java.util.HashMap());
event.getProperties().put("code_files",_code_files.toString());
event.getProperties().put("num_files",_num_files.toString());
event.getProperties().put("author",_author.toString());
event.getProperties().put("num_lines",_num_lines.toString());
event.getProperties().put("message_length",_message_length.toString());
event.getProperties().put("branch",_branch.toString());
event.getProperties().put("type",_type.toString());
event.getProperties().put("misc_files",_misc_files.toString());
event.getProperties().put("test_files",_test_files.toString());
return event;
}

}
